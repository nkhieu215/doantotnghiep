import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TanggiamtlDetailComponent } from './tanggiamtl-detail.component';

describe('Tanggiamtl Management Detail Component', () => {
  let comp: TanggiamtlDetailComponent;
  let fixture: ComponentFixture<TanggiamtlDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TanggiamtlDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TanggiamtlDetailComponent,
              resolve: { tanggiamtl: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TanggiamtlDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TanggiamtlDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tanggiamtl on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TanggiamtlDetailComponent);

      // THEN
      expect(instance.tanggiamtl).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
