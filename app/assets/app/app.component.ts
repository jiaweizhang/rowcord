import { Component } from "@angular/core"
import { ROUTER_DIRECTIVES } from "@angular/router"
@Component({
	selector: "app",
	templateUrl: "assets/app/app.html",
	directives: [ROUTER_DIRECTIVES]
})
export default class AppComponent {
	title = "Hello World"
}
