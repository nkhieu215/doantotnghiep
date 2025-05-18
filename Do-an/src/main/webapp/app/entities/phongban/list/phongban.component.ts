import { Component, NgZone, inject, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IPhongban } from '../phongban.model';
import { EntityArrayResponseType, PhongbanService } from '../service/phongban.service';
import { PhongbanDeleteDialogComponent } from '../delete/phongban-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-phongban',
  templateUrl: './phongban.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ItemCountComponent,
  ],
})
export class PhongbanComponent implements OnInit {
  subscription: Subscription | null = null;
  phongbans?: IPhongban[];
  isLoading = false;

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected phongbanService = inject(PhongbanService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IPhongban): number => this.phongbanService.getPhongbanIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  delete(phongban: IPhongban): void {
    const modalRef = this.modalService.open(PhongbanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phongban = phongban;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.phongbans = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IPhongban[] | null): IPhongban[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page } = this;

    this.isLoading = true;
    const pageToLoad: number = page;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.phongbanService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
