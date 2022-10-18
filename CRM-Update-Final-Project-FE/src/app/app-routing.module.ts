import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ErrorComponent} from "./layouts/error/error.component";

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                {
                    path: 'login',
                    loadChildren: () => import('../app/pages/components/login/login.module').then(m => m.LoginModule)
                },
                {
                    path: 'project',
                    loadChildren: () => import('../app/pages/components/project/project.module').then(m => m.ProjectModule)
                },
                {
                    path: 'task',
                    loadChildren: () => import('../app/pages/components/task/task.module').then(m => m.TaskModule)
                },
                {
                    path: 'user',
                    loadChildren: () => import('../app/pages/components/staff/staff.module').then(m => m.StaffModule)
                },
                {
                    path: '',
                    redirectTo: 'login',
                    pathMatch: 'full'
                },
                {
                    path: '**',
                    component: ErrorComponent
                }
            ],
            {enableTracing: true}
        ),
    ],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
