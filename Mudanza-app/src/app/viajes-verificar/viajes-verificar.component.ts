import { Component, OnInit } from '@angular/core';
import { ViajesService } from '../viajes.service';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { ArchivosService } from '../archivos.service';

@Component({
  selector: 'app-viajes-verificar',
  templateUrl: './viajes-verificar.component.html',
  styleUrls: ['./viajes-verificar.component.css']
})
export class ViajesVerificarComponent implements OnInit {
  archivo: File = null;
  logger = new FormControl('');
  documento = new FormControl('');
  resource: string;
  resultado: Array<any>;
  progreso: string;
  mostrarBarra: boolean;

  constructor(public serviceViajes: ViajesService,
    public serviceArchivos: ArchivosService,
    public router: Router) { }

  ngOnInit(): void {
    this.mostrarBarra = false;
  }

  handleFileInput(files: FileList) {
    this.archivo = files.item(0);
  }

  cargarArchivo(): void {
    this.mostrarBarra = true;
    this.serviceArchivos.postFile(this.archivo).subscribe(
      resp => {
        console.log(resp);
        if (resp.codigo == 200) {
          this.resource = resp.recursoId;
          this.verificarViajes();
        }
      },
      err => {
        if (err.status == 400) {
          alert('El archivo seleccionado cuenta con errores en su estructura');
        }

        if (err.status == 500) {
          alert('Se presento un error interno al procesar la solicitud. Por favor intenta nuevamente')
        }
      }
    );
  }

  verificarViajes(): void {
    let casos = 1;
    this.progreso = "";
    this.resultado = [];
    this.serviceViajes.verificarViaje(this.resource, this.documento.value).subscribe(
      x => {
        this.logger.setValue(this.logger.value + 'Observer got a next value: ' + x.data + '\n');
        this.resultado.push('Case #' + casos + ':' + JSON.parse(x.data).cantidadViajes+'\n');
        this.progreso = "" + (this.resultado.length / JSON.parse(x.data).cantidadCasos) * 100;
        casos += 1;
      },
      err => this.logger.setValue('Observer got an error: ' + err),
      () => {
        this.logger.setValue(this.logger.value + 'Observer got a complete notification');
        this.crearArchivoTexto(this.resultado.toString())
      }
    );
  }

  crearArchivoTexto(texto: string): void {
    var textFile = null;
    var data = new Blob([texto.replace(/,/g, "")], { type: 'text/plain' });
    if (textFile !== null) {
      window.URL.revokeObjectURL(textFile);
    }
    textFile = window.URL.createObjectURL(data);

    const a: HTMLAnchorElement = document.createElement(
      'a'
    ) as HTMLAnchorElement;
    a.href = textFile;
    a.download = 'resultado.txt';
    a.text = 'Descargar resultado';
    a.className = 'btn btn-primary mt-md-4';
    document.getElementById('resultadoCarga').innerHTML = '';
    document.getElementById('resultadoCarga').appendChild(a);
    this.mostrarBarra = false;
  };
}
