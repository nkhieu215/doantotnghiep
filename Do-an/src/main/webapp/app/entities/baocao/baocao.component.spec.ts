import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaocaoComponent } from './baocao.component';

describe('BaocaoComponent', () => {
  let component: BaocaoComponent;
  let fixture: ComponentFixture<BaocaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BaocaoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BaocaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
