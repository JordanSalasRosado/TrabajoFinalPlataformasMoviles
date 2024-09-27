using Amazon.Lambda.APIGatewayEvents;
using Amazon.Lambda.Core;
using Newtonsoft.Json;
using RSTLambda.Entities;
using RSTLambda.Repositories;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.SystemTextJson.DefaultLambdaJsonSerializer))]

namespace ListarAtencion;

public class Function
{

    /// <summary>
    /// A simple function that takes a string and does a ToUpper
    /// </summary>
    /// <param name="input">The event for the Lambda function handler to process.</param>
    /// <param name="context">The ILambdaContext that provides methods for logging and describing the Lambda environment.</param>
    /// <returns></returns>
    public APIGatewayProxyResponse FunctionHandler(APIGatewayProxyRequest request, ILambdaContext context)
    {
        try
        {
            var atenciones = new List<Atencion>();

            if (request.QueryStringParameters.TryGetValue("filter", out var filter))
            {
                atenciones = new AtencionRepository().ListarAtenciones(filter);
            }
            else
            {
                atenciones = new AtencionRepository().ListarAtenciones();
            }
           
            return new APIGatewayProxyResponse
            {
                StatusCode = 200,
                Body = JsonConvert.SerializeObject(atenciones),
                Headers = new Dictionary<string, string> {
                    { "Content-Type", "application/json" }
                }
            };
        }
        catch (Newtonsoft.Json.JsonException ex)
        {
            return new APIGatewayProxyResponse
            {
                StatusCode = 400,
                Body = JsonConvert.SerializeObject(new { error = $"An error ocurred while executing the function [{ex.Message}]" }),
                Headers = new Dictionary<string, string> {
                    { "Content-Type", "application/json" }
                }
            };
        }
        catch (Exception ex)
        {
            return new APIGatewayProxyResponse
            {
                StatusCode = 500,
                Body = JsonConvert.SerializeObject(new { error = $"An error ocurred while executing the function [{ex.Message}] [{ex.StackTrace}] [{JsonConvert.SerializeObject(request)}]" }),
                Headers = new Dictionary<string, string> {
                    { "Content-Type", "application/json" }
                }
            };
        }
    }
}
