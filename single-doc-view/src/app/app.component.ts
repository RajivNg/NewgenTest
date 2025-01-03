import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { FileUploadService } from './../shared/file-upload-service.service';
import { NgxSpinnerService } from 'ngx-spinner';

// Extending the global Window interface to include pData
declare global {
  interface Window {
    pData?: {
      ActivityID: string;
      EngineName: string;
      ProcessInstanceID: string;
      WorkItemID: string;
      SessionID: string;
      RouteID: string;
    };
  }

  // Extending the File interface to include uniqueName
  interface File {
    uniqueName?: string;
  }
}





@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements AfterViewInit {
  @ViewChild('attachments') attachment: any;
  @ViewChild('uploadModal') uploadModal: any;
  @ViewChild('fileInput') fileInput!: ElementRef;

  typeSelected: string = 'ball-scale-multiple';
  fileList: File[] = [];
  listOfFiles: string[] = [];
  isLoading = false;

  ActivityID = '';
  EngineName = '';
  ProcessInstanceID = '';
  RouteID = '';
  SessionID = '';
  WorkItemID = '';
  spinnerMessage = 'Please wait while we are uploading your documents';
  caseNo = '';
  interval: number = 1000;

  allowedExtensions = ['tif', 'tiff', 'jpg', 'jpeg', 'png', 'pdf'];

  constructor(
    private fileUploadService: FileUploadService,
    private spinnerService: NgxSpinnerService
  ) {
    // Fetching properties during initialization
    this.fileUploadService.readProperties().subscribe((response: any) => {
      this.interval = parseInt(response.Timer, 10);
      console.log('Interval is:', this.interval);
    });
  }

  ngAfterViewInit() {
    // Modal handling logic can be added here if necessary
  }

  ngOnInit() {
    // Accessing pData from the window object safely
    const pData = window.pData;
    if (pData) {
      this.ActivityID = pData.ActivityID;
      this.EngineName = pData.EngineName;
      this.ProcessInstanceID = pData.ProcessInstanceID;
      this.WorkItemID = pData.WorkItemID;
      this.SessionID = pData.SessionID;
      this.RouteID = pData.RouteID;
    }

    console.log(this.ActivityID, this.EngineName);
    this.fileUploadService.readProperties();
  }

  closeScreen() {
    window.close();
  }

  onFileChanged(event: any) {
    this.isLoading = true;
    let unsupportedFileFound = false;
    Array.from(event.target.files).forEach((file: File) => {

      let fileName = file.name.replace(/[&;']/g, '_'); // Replace '&' with a space
      file = new File([file], fileName, { type: file.type }); // Create a new File object with the updated name
          
      const fileExtension = file.name.split('.').pop()?.toLowerCase();
      if (!this.allowedExtensions.includes(fileExtension || '')){
        unsupportedFileFound = true;
        //alert('File Format not supported');
      }
      else{
      const uniqueFileName = this.generateUniqueFileName(file.name);
      if (this.isFileAlreadyExists(uniqueFileName)) {
        alert('Error: File with the same name already exists.');
      } else {
        file.uniqueName = uniqueFileName; // Adding uniqueName to File object
        this.fileList.push(file);
        this.listOfFiles.push(uniqueFileName);
        console.log("FileList:: ",this.fileList);
      }
    }
    });
    if (unsupportedFileFound) {
      alert('File Format not supported');
    }
    this.isLoading = false;
  }

  resetFileInput() {
    if (this.fileInput) {
      this.fileInput.nativeElement.value = ''; // Reset the file input
    }
  }

  generateUniqueFileName(fileName: string): string {
    return fileName; // This can be enhanced to generate truly unique names
  }

  isFileAlreadyExists(uniqueFileName: string): boolean {
    return this.listOfFiles.includes(uniqueFileName);
  }

  removeSelectedFile(index: number) {
    this.listOfFiles.splice(index, 1);
    this.fileList.splice(index, 1);
    if (this.listOfFiles.length === 0 && this.fileList.length === 0) {
      this.resetFileInput();
    }
  }

  uploadFiles() {
    if (this.fileList.length === 0) {
      alert('Please attach document to proceed.');
      return;
    }

    this.spinnerService.show();
    let parameters = {
      ActivityID: this.ActivityID,
      EngineName: this.EngineName,
      ProcessInstanceID: this.ProcessInstanceID,
      WorkItemID: this.WorkItemID,
      SessionID: this.SessionID,
      RouteID: this.RouteID,
    };

    const formData = new FormData();
    formData.append('properties', JSON.stringify(parameters));

    this.fileList.forEach((file) => {
      formData.append('files', file);
    });

    this.fileUploadService.uploadFiles(formData).subscribe(
      (response: any) => {
        console.log(response);
        let resultObj: any = response;
        if (resultObj.Status === '0') {
          this.spinnerMessage = resultObj.response;
          this.caseNo = resultObj.CASE_NO;
          const param = {
            ProcessInstanceID: this.caseNo,
            SessionID: this.SessionID,
            EngineName: this.EngineName,
          };
          setInterval(() => this.callServlet(param), this.interval);
        }
      },
      (error) => {
        console.error('Error during file upload:', error);
        this.spinnerService.hide();
        alert('Some error occurred.');
        window.close();
      }
    );
  }

  callServlet(dataForClassified: any) {
    this.fileUploadService.checkClassified(dataForClassified).subscribe(
      (response: any) => {
        if (response.response.toLowerCase() === 'yes') {
          this.spinnerService.hide();
          const UrlPrefix1 =
            window.location.protocol +
            '//' +
            window.location.hostname +
            (window.location.port ? ':' + window.location.port : '');
          const servleturl1 = `${UrlPrefix1}/MUHDMS/servlet/RedirectJSP`;

          window.location.href =
            `${servleturl1}?ProcessInstanceID=${encodeURIComponent(
              this.caseNo
            )}` +
            `&WorkitemID=${encodeURIComponent(this.WorkItemID)}` +
            `&QueueId=${encodeURIComponent(this.ActivityID)}` +
            `&RouteId=${encodeURIComponent(this.RouteID)}`;
        }
      },
      (error) => {
        console.error('Error sending redirect request:', error);
      }
    );
  }
}