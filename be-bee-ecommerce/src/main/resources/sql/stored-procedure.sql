create procedure sp_getParentByChildrenId(@childrenId bigint)
    as
begin
    --Tạo bảng tạm
    DECLARE @value TABLE
                   (
                       id         BIGINT,
                       name       NVARCHAR(225),
                       poster_url NVARCHAR(225),
                       level      int,
                       parent_id  bigint
                   )

    --Biến lưu giá trị id cha của id con
    DECLARE @parenId bigint;

    SET @parenId = (select parent_id from category where id = @childrenId)

    WHILE @parenId IS NOT NULL
        BEGIN
        INSERT INTO @value (id, name, poster_url, parent_id, level)
        SELECT id, name, poster_url, parent_id, level
        FROM category
        WHERE id = @parenId
            SET @parenId = (select parent_id from @value where id = @parenId)
        END

    SELECT * FROM @value
end
go

