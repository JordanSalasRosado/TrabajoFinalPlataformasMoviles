USE unimaqrst
GO 


CREATE PROCEDURE usp_listar_maquinaria  
 @filter VARCHAR(100) = NULL  
AS  
BEGIN  
  
 SELECT [id]  
     ,[marca] AS 'Marca'  
     ,[categoria] AS 'Categoria'  
     ,[modelo] AS 'Modelo'  
     ,[imagen] AS 'Imagen'  
 FROM dbo.maquinaria m WITH(NOLOCK)  
 WHERE @filter IS NULL OR (  
  m.marca LIKE '%'+ @filter +'%'  
  OR m.categoria LIKE '%'+ @filter +'%'  
  OR m.modelo LIKE '%'+ @filter +'%'  
 )  
      
END