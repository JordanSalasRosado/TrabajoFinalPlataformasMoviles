USE unimaqrst
GO 


CREATE PROCEDURE usp_listar_taller
 @filter VARCHAR(100) = NULL  
AS  
BEGIN  
  
 SELECT [id]  
     ,[latitud] AS 'Latitud'  
     ,[longitud] AS 'Longitud'  
     ,[nombre] AS 'Nombre'  
     ,[descripcion] AS 'Descripcion'  
	 ,[activo] AS 'Active'
 FROM dbo.taller t WITH(NOLOCK)  
 WHERE activo = 1 AND ( @filter IS NULL OR (  
  t.nombre LIKE '%'+ @filter +'%'  
  OR t.descripcion LIKE '%'+ @filter +'%' 
 )  )
      
END