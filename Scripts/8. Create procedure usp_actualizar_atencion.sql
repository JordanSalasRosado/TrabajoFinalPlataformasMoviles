USE [UnimaqRST]
GO

CREATE PROCEDURE usp_actualizar_atencion
	@id BIGINT,
	@numero_solicitud VARCHAR(50),
	@id_maquinaria BIGINT,
	@numero_serie VARCHAR(50),
	@anho_fabricacion INT,
	@horometro VARCHAR(100),
	@stir_2 VARCHAR(100),
	@observaciones VARCHAR(MAX)
AS
BEGIN

	DECLARE @Now DATETIME = GETDATE()

	UPDATE [dbo].[atencion]
	SET [numero_solicitud] = @numero_solicitud
           ,[id_maquinaria] = @id_maquinaria
           ,[numero_serie] = @numero_serie
           ,[anho_fabricacion] = @anho_fabricacion
           ,[horometro] = @horometro
           ,[stir_2] = @stir_2
           ,[observaciones] = @observaciones
           ,[fecha_modificacion] = GETDATE()
	WHERE id = @id
END