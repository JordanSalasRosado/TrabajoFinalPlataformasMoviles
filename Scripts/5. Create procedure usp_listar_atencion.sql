USE [UnimaqRST]
GO

CREATE PROCEDURE usp_listar_atencion
	@filter VARCHAR(100) = NULL
AS
BEGIN

	SELECT a.[id]
		   ,[numero_solicitud] AS 'NumeroSolicitud'
           ,[id_maquinaria] AS 'IdMaquinaria'
           ,[numero_serie] AS 'NumeroSerie'
           ,[anho_fabricacion] AS 'AnhoFabricacion'
           ,[horometro] AS 'Horometro'
           ,[stir_2] AS 'Stir2'
           ,[revision_hidraulica] AS 'RevisionHidraulica'
           ,[revision_aceite] AS 'RevisionAceite'
           ,[revision_calibracion] AS 'RevisionCalibracion'
           ,[revision_neumaticos] AS 'RevisionNeumaticos'
           ,[observaciones] AS 'Observaciones'
           ,[firma_tecnico] AS 'FirmaTecnico'
           ,[firma_supervisor] AS 'FirmaSupervisor'
           ,[fecha_creacion] AS 'FechaCreacion'
           ,[fecha_modificacion] AS 'FechaModificacion'
		   ,m.marca AS 'MaquinariaMarca'
		   ,m.categoria AS 'MaquinariaCategoria'
		   ,m.modelo AS 'MaquinariaModelo'
		   ,m.imagen AS 'MaquinariaImagen'
	FROM dbo.atencion a WITH(NOLOCK)
	INNER JOIN dbo.maquinaria m WITH(NOLOCK) ON m.id = a.id_maquinaria
	WHERE @filter IS NULL OR (
		a.numero_solicitud LIKE '%'+ @filter +'%'
		OR a.numero_serie LIKE '%'+ @filter +'%'
		OR a.horometro LIKE '%'+ @filter +'%'
		OR m.marca LIKE '%'+ @filter +'%'
		OR m.categoria LIKE '%'+ @filter +'%'
		OR m.modelo LIKE '%'+ @filter +'%'
	)
    
END