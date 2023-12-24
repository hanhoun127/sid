DROP DATABASE IF EXISTS pharmacie ;

CREATE DATABASE pharmacie;

use pharmacie

CREATE TABLE pharmacie (
    barcode VARCHAR(13) NOT NULL PRIMARY KEY,
    articleName VARCHAR(255) NOT NULL UNIQUE,
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

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936082038','Compresses Steriles 7.5cm x 7.5cm','95.00','100');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936085487','DOLIPRANE 100MG B/10 SUPPO ','118,7','25');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936088461','DOLIPRANE 200MG B/10 SUPPO','116,6','12');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936045512','AMOXICILLINE-BG 1G B/06 SACHET','149,07','15');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135935165995','AMOXICILLINE-BG 1G B/14 SACHET','318,94','10');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936054518','AMOXIMEX 1G B/14 COMP DISP','297,5','5');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135935559562','VITAMINE B 6 250MG/5ML B/05 SOL INJ ','139,81','11');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936088465','VITAMINE B1 100MG/ML B/05 SOL INJ','80,37','9');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936451838','VITAMINE A FAURE 0.15MUI F/10ML COLLYRE','145,2','7');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936085438','VITAMINE B12 1000 G/4ML B/6AM SOL INJ','190,31','3');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936545854','VITAMINE C 500MG B/20 COMP A CROQUER','156,73','13');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936246254','VITAMINE C + ZINC B/60 GELULE','538,43','16');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936544641','VITAMINE C 500MG B/16 COMP EFF','124,26','21');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936544632','VITAMINE D3 0.2MUI/ML B/1AMP/1ML ET B/01 SOL INJ','97,22','12');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936548745','BETADINE DERMIQUE 10% F/125ML LOTION DERM','121,62','30');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936545132','BETADINE SCRUB 4% F/125ML SOL DERM','118,74','23');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135945131121','ASPEGIC 100MG B/20 PDRE SACHET','113,1','11');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135938714513','ASPEGIC 250MG B/20 PDRE SACHET','128,95','7');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936571456','ASPEGIC 500MG B/20 PDRE SACHET','129,45','17');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135985415612','ASPEGIC 500MG B/06 5ML SOL INJ','300,35','6');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135935158486','ASPIRINE 500MG B/20 COMP','98,21','4');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135936745633','ASPIRINE UPSA 500MG B/20 COMP EFF','102,42','18');

INSERT INTO `pharmacie`(`barcode`, `articleName`, `articlePrice`, `stock`) VALUES ('6135954846464','ASPIRINE UPSA VIT C B/20 COMP','100,64','19');




