import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  private readonly baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = `${window.location.protocol}//${window.location.hostname}${window.location.port ? `:${window.location.port}` : ''}`;
  }

  private getServletUrl(endpoint: string): string {
    return `${this.baseUrl}/MUHDMS/servlet/${endpoint}`;
  }

  uploadFiles(formData: FormData) {
    const servletUrl = this.getServletUrl('AddDocument');
    return this.http.post(servletUrl, formData);
  }

  redirectJsp() {
    const servletUrl = this.getServletUrl('RedirectJSP');
    return this.http.post(servletUrl, {});
  }

  checkClassified(params: any) {
    const servletUrl = this.getServletUrl('CheckClassified');
    return this.http.post(servletUrl, params);
  }

  readProperties() {
    const servletUrl = this.getServletUrl('UploadPropertyReader');
    return this.http.post(servletUrl, {});
  }
}
