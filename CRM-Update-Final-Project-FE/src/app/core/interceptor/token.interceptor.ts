import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {LocalStorageService} from "ngx-webstorage";
import {Observable} from 'rxjs';
import {AppSettings} from "../../app.constants";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(
        private localStorageService: LocalStorageService
    ) {
    }

    intercept(
        request: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        const authData = this.localStorageService.retrieve(AppSettings.AUTH_DATA);
        if (authData != null) {
            const modifiedReq = request.clone({
                headers: request.headers.set('Authorization', `Bearer ${authData.accessToken}`),
            });
            return next.handle(modifiedReq);
        }
        return next.handle(request);
    }
}