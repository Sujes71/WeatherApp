import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Obtén el token JWT almacenado en localStorage (o donde lo hayas guardado)
        const token = localStorage.getItem('jwtToken');

        if (token) {
            // Agrega el encabezado Authorization con el token JWT
            const cloned = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });

            return next.handle(cloned);
        } else {
            return next.handle(request);
        }
    }
}
