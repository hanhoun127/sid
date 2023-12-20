DROP DATABASE IF EXISTS pharmacie ;
CREATE DATABASE pharmacie;
use pharmacie

CREATE TABLE pharmacie (
    barcode VARCHAR(13) NOT NULL PRIMARY KEY,
    articleName VARCHAR(255) NOT NULL ,
    articlePrice DOUBLE NOT NULL,
    stock INT NOT NULl
);

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6132353100640','Unisia 5mg/8mg BTE/30','1500.00','15');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6132568700640','Solupred Oro 20 mg bte/20','392.00','25');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6132348900927','Lisinox 40mg BTE/14','451.63','20');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6132347409020','Loratadine Beker 10mg BTE/30','180.00','30');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6130004120092','Histagan 2mg BTE/30','150.00','40');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6131115202007','Dipronad 5mg/2mg INJ','357.63','46');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('3400932857012','Biafine 168g','800.00','12');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936000030','Alcool Chirurgical 70° 250ml','250.00','60');
INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936082038','Compresses Steriles 7.5cm x 7.5cm ','95.00','100');
