import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

@Component({
  selector: 'jhi-baocao',
  standalone: true,
  imports: [
    FormsModule],
  templateUrl: './baocao.component.html',
  styleUrl: './baocao.component.scss'
})
export class BaocaoComponent {
  selectedReportType: string = 'tax'; // Giá trị mặc định
}
