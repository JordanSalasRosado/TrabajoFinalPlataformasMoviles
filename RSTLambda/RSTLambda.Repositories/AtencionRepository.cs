using Dapper;
using RSTLambda.Entities;
using System.Data;
using System.Data.SqlClient;
using static Dapper.SqlMapper;

namespace RSTLambda.Repositories
{
    public class AtencionRepository
    {
        private readonly string _connectionString = "Data Source=unimaq-rst.c5ecma2mmc1p.us-east-2.rds.amazonaws.com;Initial Catalog=UnimaqRST;User ID=admin;Password=Pa$$w0rd2024.RST;Application Name=rst-lambda";

        public AtencionRepository()
        {

        }

        public Atencion CrearAtencion(Atencion entity)
        {
            SqlConnection? connection = null;

            using (connection = new SqlConnection(_connectionString))
            {
                var id = connection.QuerySingle<long>("usp_crear_atencion",
                    new
                    {
                        numero_solicitud = entity.NumeroSolicitud,
                        id_maquinaria = entity.IdMaquinaria,
                        numero_serie = entity.NumeroSerie,
                        anho_fabricacion = entity.AnhoFabricacion,
                        ultimo_mantenimiento = entity.UltimoMantenimiento,
                        horometro = entity.Horometro,
                        stir_2 = entity.Stir2,
                        revision_hidraulica = entity.RevisionHidraulica,
                        revision_aceite = entity.RevisionAceite,
                        revision_calibracion = entity.RevisionCalibracion,
                        revision_neumaticos = entity.RevisionNeumaticos,
                        observaciones = entity.Observaciones,
                        firma_tecnico = entity.FirmaTecnico,
                        firma_supervisor = entity.FirmaSupervisor
                    },
                    commandType: CommandType.StoredProcedure);

                entity.Id = id;
            }

            return entity;
        }

        public Atencion? GetAtencion(long id)
        {
            SqlConnection? connection = null;

            using (connection = new SqlConnection(_connectionString))
            {
                return connection.Query<Atencion>("usp_get_atencion", new { id = id }, commandType: CommandType.StoredProcedure).FirstOrDefault();
            }
        }

        public List<Atencion> ListarAtenciones(string filter = "")
        {
            SqlConnection? connection = null;

            using (connection = new SqlConnection(_connectionString))
            {
                if (string.IsNullOrEmpty(filter))
                {
                    return connection.Query<Atencion>("usp_listar_atencion", null, commandType: CommandType.StoredProcedure).ToList();
                }
                else
                {                    
                    return connection.Query<Atencion>("usp_listar_atencion", new { filter }, commandType: CommandType.StoredProcedure).ToList();
                }
            }
        }
    }
}
