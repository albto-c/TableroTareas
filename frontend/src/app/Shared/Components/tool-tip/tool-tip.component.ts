import { Component, input } from '@angular/core'
import { CommonModule } from '@angular/common'

@Component({
    selector: 'app-tool-tip',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './tool-tip.component.html',
    styleUrl: './tool-tip.component.css',
})
export class ToolTipComponent {
    status = input<number>()
    text = input<String>()
}
