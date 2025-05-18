import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BangchamcongDetailComponent } from './bangchamcong-detail.component';

describe('Bangchamcong Management Detail Component', () => {
  let comp: BangchamcongDetailComponent;
  let fixture: ComponentFixture<BangchamcongDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BangchamcongDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BangchamcongDetailComponent,
              resolve: { bangchamcong: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BangchamcongDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BangchamcongDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bangchamcong on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BangchamcongDetailComponent);

      // THEN
      expect(instance.bangchamcong).toEqual(expect.objectContaining({ id: 123 }));
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
