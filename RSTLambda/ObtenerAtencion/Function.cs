using Amazon.Lambda.APIGatewayEvents;
using Amazon.Lambda.Core;
using Newtonsoft.Json;
using RSTLambda.Entities;
using RSTLambda.Repositories;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.SystemTextJson.DefaultLambdaJsonSerializer))]

namespace ObtenerAtencion;

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

            if (request.QueryStringParameters.TryGetValue("id", out var filter))
            {
                if (!string.IsNullOrEmpty(filter))
                {

                    if (long.TryParse(filter, out var id))
                    {
                        var atencion = new AtencionRepository().ObtenerAtencion(id);

                        if (atencion != null)
                        {
                            return new APIGatewayProxyResponse
                            {
                                StatusCode = 200,
                                Body = JsonConvert.SerializeObject(atencion),
                                Headers = new Dictionary<string, string> { { "Content-Type", "application/json" } }
                            };
                        }
                    }
                }
            }

            return new APIGatewayProxyResponse
            {
                StatusCode = 400,
                Body = JsonConvert.SerializeObject(new { error = $"An error ocurred parsing id parameter" }),
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
