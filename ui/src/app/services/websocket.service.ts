import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Router} from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable()
export class WebsocketService {
  private socket: any;
  private connected = false;
  subscriptions: any;
  websocketMessages: string | undefined;

  private websocketConnectedService = new BehaviorSubject<boolean>(false);
  websocketConnectedObservable = this.websocketConnectedService.asObservable();

  constructor(private router: Router) {
    this.subscriptions = new Map<string, object>();
  }

  setConnected() {
    this.websocketConnectedService.next(true);
  }

  public isConnected(): boolean {
      return this.connected;
  }

  public initialize(): void {
    this.createWebsocket();
  }

  public createWebsocket() {
    this.socket = new WebSocket(environment.websocketUrl);

      const parentscope = this;
      this.socket.addEventListener(WebsocketAction.WS_CONNECT, (event: any) => {
        console.log('Connected');
        parentscope.connected = true;
        parentscope.setConnected();
      });

      this.socket.addEventListener(WebsocketAction.WS_DISCONNECT, (event: any) => {
        console.log('Disconnected');
        parentscope.websocketConnectedService.next(false);
        parentscope.connected = false;
      });

      this.socket.addEventListener(WebsocketAction.WS_MESSAGE, (message: MessageEvent) => {
        console.log('Message Received');
        console.log(message);
        this.websocketMessages += message + '<br>';
      });
  }

  // do we want subscriptions for tickers?
  public subscribeToEvent(channel: any, callback: any) {
      console.log('Subscribing to ' + channel);
      this.subscriptions.set(channel, callback);
  }

  public sendEvent(message: any): void {
    if (this.isConnected()) {
      this.websocketMessages += message + '<br>';
      console.log('sending out message: ', message);
      this.socket.send(JSON.stringify(message));
    }
  }

}

export enum WebsocketAction {
    WS_CONNECT = 'open',
    WS_DISCONNECT = 'close',
    WS_ERROR = 'error',
    WS_MESSAGE = 'message'
  }

