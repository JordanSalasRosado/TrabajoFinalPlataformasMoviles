using Dapper;
using RSTLambda.Entities;
using System.Data;
using System.Data.SqlClient;

namespace RSTLambda.Repositories
{
    public class MaquinariaRepository
    {
        private readonly string _connectionString = "Data Source=unimaq-rst.c5ecma2mmc1p.us-east-2.rds.amazonaws.com;Initial Catalog=UnimaqRST;User ID=admin;Password=Pa$$w0rd2024.RST;Application Name=rst-lambda";

        public List<Maquinaria> ListarMaquinarias(string filter = "")
        {
            SqlConnection? connection = null;

            using (connection = new SqlConnection(_connectionString))
            {
                if (string.IsNullOrEmpty(filter))
                {
                    return connection.Query<Maquinaria>("usp_listar_maquinaria", null, commandType: CommandType.StoredProcedure).ToList();
                }
                else
                {
                    return connection.Query<Maquinaria>("usp_listar_maquinaria", new { filter }, commandType: CommandType.StoredProcedure).ToList();
                }
            }
        }
    }
}
