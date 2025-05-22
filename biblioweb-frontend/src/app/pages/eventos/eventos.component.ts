import { Component } from '@angular/core';


@Component({
  selector: 'app-eventos',
  standalone: false,
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.scss']
})
export class EventosComponent {
  eventos = [
    {
      titulo: 'Cuentacuentos',
      descripcion: 'Sesión de lectura animada para niños, donde se narran cuentos clásicos y contemporáneos en voz alta, fomentando la imaginación, la escucha activa y el amor por la lectura desde temprana edad.',
      fecha: 'Martes a las 17:00 h',
      imagen: './cuentos.jpeg'
    },
    {
      titulo: 'Taller de Manualidades',
      descripcion: 'Espacio creativo para niños y niñas donde podrán experimentar con papel, pintura, cartón y materiales reciclados. Se fomentará la expresión artística, la motricidad fina y la conciencia ecológica.',
      fecha: 'Viernes a las 17:00 h',
      imagen: './talleres.jpg'
    },
    {
      titulo: 'Cine Infantil',
      descripcion: 'Proyección gratuita de una película especialmente seleccionada para el público infantil, en un ambiente familiar y cómodo. Una oportunidad para disfrutar del cine y compartir con otros niños.',
      fecha: 'Sábado a las 19:00 h',
      imagen: './cine.jpg'
    }
  ];
}
