namespace RSTLambda.Entities
{
    public class Taller
    {
        public long Id { get; set; }
        public decimal Latitud { get; set; }
        public decimal Longitud { get; set; }
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
        public bool Activo { get; set; }
    }
}
