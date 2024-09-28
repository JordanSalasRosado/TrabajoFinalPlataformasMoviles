namespace RSTLambda.Entities
{
    public class Atencion
    {
        public long Id { get; set; }
        public string NumeroSolicitud { get; set; }
        public long IdMaquinaria { get; set; }
        public string NumeroSerie { get; set; }
        public int AnhoFabricacion { get; set; }
        public string UltimoMantenimiento { get; set; }
        public string Horometro { get; set; }
        public string Stir2 { get; set; }
        public bool RevisionHidraulica { get; set; }
        public bool RevisionAceite { get; set; }
        public bool RevisionCalibracion { get; set; }
        public bool RevisionNeumaticos { get; set; }
        public string Observaciones { get; set; }
        public string FirmaTecnico { get; set; }
        public string FirmaSupervisor { get; set; }
        public DateTime FechaCreacion { get; set; }
        public DateTime FechaModificacion { get; set; }
        public string MaquinariaMarca { get; set; }
        public string MaquinariaCategoria { get; set; }
        public string MaquinariaModelo { get; set; }
        public string MaquinariaImagen { get; set; }
    }
}
