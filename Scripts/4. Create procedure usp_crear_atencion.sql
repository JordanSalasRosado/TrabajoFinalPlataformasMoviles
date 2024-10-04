USE [UnimaqRST]
GO

CREATE PROCEDURE usp_crear_atencion
	@numero_solicitud VARCHAR(50),
	@id_maquinaria BIGINT,
	@numero_serie VARCHAR(50),
	@anho_fabricacion INT,
	@horometro VARCHAR(100),
	@stir_2 VARCHAR(100),
	@revision_hidraulica BIT,
	@revision_aceite BIT,
	@revision_calibracion BIT,
	@revision_neumaticos BIT,
	@observaciones VARCHAR(MAX),
	@firma_tecnico VARCHAR(150),
	@firma_supervisor VARCHAR(150)
AS
BEGIN

	DECLARE @Now DATETIME = GETDATE()

	INSERT INTO [dbo].[atencion]
           ([numero_solicitud]
           ,[id_maquinaria]
           ,[numero_serie]
           ,[anho_fabricacion]
           ,[horometro]
           ,[stir_2]
           ,[revision_hidraulica]
           ,[revision_aceite]
           ,[revision_calibracion]
           ,[revision_neumaticos]
           ,[observaciones]
           ,[firma_tecnico]
           ,[firma_supervisor]
           ,[fecha_creacion]
           ,[fecha_modificacion])
     VALUES
           (@numero_solicitud,
		   @id_maquinaria, 
		   @numero_serie,
		   @anho_fabricacion,
		   @horometro,
		   @stir_2,
		   @revision_hidraulica,
		   @revision_aceite,
		   @revision_calibracion,
		   @revision_neumaticos,
		   @observaciones,
		   @firma_tecnico,
		   @firma_supervisor,
		   @Now,
		   @Now)

	SELECT CAST(SCOPE_IDENTITY() AS INT);
END