SET GLOBAL tx_isolation='read-committed';
SET tx_isolation = 'read-committed' ;

SET GLOBAL tx_isolation = 'REPEATABLE-READ' ;
SET tx_isolation = 'REPEATABLE-READ' ;

-- innodb【REPEATABLE-READ】使用【next-key locking】解决了幻读的问题
SELECT @@GLOBAL.tx_isolation, @@tx_isolation;
 
SET autocommit = 0;

START TRANSACTION; 
 
SELECT * FROM pt_inb_order;

SELECT * FROM pt_inb_order;
 
COMMIT;
------------------------------------------------

SELECT @@GLOBAL.tx_isolation, @@tx_isolation;

SET autocommit = 0;

START TRANSACTION;

INSERT INTO pt_inb_order(orderNo)VALUES('O1001');

COMMIT;