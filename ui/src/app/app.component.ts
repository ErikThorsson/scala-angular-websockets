import { Component } from '@angular/core';

import { AppService } from './app.service';
import { WebsocketService } from './services/websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string | undefined;
  postRequestResponse: string | undefined;

  constructor(private appService: AppService, private websocketService: WebsocketService) {
    console.log('initializing websocket service...');
    websocketService.initialize();

    // subscription with callback example
    this.websocketService.subscribeToEvent('test-subscription', (data: any) => {
      console.log('received event ');
    });
  }

  /**
   * This method is used to test the post request
   */
  public postData(): void {
    this.appService.sendData().subscribe((data: any) => {
      this.postRequestResponse = data.content;
    });
  }

  connect() {
    this.websocketService.createWebsocket();
  }

  sendWebsocketEvent() {
    const msg = 'Hello!';
    this.websocketService.sendEvent(msg);
  }
}
