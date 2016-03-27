DROP TABLE IF EXISTS a;
DROP TABLE IF EXISTS b;
DROP TABLE IF EXISTS c;
DROP TABLE IF EXISTS d;
DROP TABLE IF EXISTS e;
DROP TABLE IF EXISTS f;

CREATE TABLE IF NOT EXISTS a (
  a_id INTEGER PRIMARY KEY AUTOINCREMENT,
  c_id INTEGER,
  VALUE VARCHAR(45) NULL,

  FOREIGN KEY(c_id) REFERENCES c(c_id)
);

CREATE TABLE IF NOT EXISTS b (
  b_id INTEGER PRIMARY KEY AUTOINCREMENT,
  e_id INTEGER,
  c_id INTEGER,
  VALUE VARCHAR(45) NULL,

  FOREIGN KEY(e_id) REFERENCES e(e_id),
  FOREIGN KEY(c_id) REFERENCES c(c_id)
);

CREATE TABLE IF NOT EXISTS c (
  c_id INTEGER PRIMARY KEY AUTOINCREMENT,
  f_id INTEGER,
  VALUE VARCHAR(45) NULL,

  FOREIGN KEY(f_id) REFERENCES f(f_id)
);

CREATE TABLE IF NOT EXISTS d (
  d_id INTEGER PRIMARY KEY AUTOINCREMENT,
  b_id INTEGER,
  c_id INTEGER,
  VALUE VARCHAR(45) NULL,

  FOREIGN KEY(b_id) REFERENCES b(b_id),
  FOREIGN KEY(c_id) REFERENCES c(c_id)
);

CREATE TABLE IF NOT EXISTS e (
  e_id INTEGER PRIMARY KEY AUTOINCREMENT,
  VALUE VARCHAR(45) NULL
);

CREATE TABLE IF NOT EXISTS f (
  f_id INTEGER PRIMARY KEY AUTOINCREMENT,
  b_id INTEGER,
  VALUE VARCHAR(45) NULL,

  FOREIGN KEY (b_id) REFERENCES b(b_id)
);

INSERT INTO e (VALUE) VALUES ('BYL34SBB4VD'),('TCK00EDA7QB'),('FEC61ZWD3WX'),('ZWR83ENA8KL'),('EXH10WFH5AS'),('KBE80JDC0AQ'),('DLD08VJG0ZF'),('KMI46SHG3WB'),('EAI89UKK5II'),('SYA67BRQ8PN'),('DZU91IZC9OD'),('PPP63QJJ4OK'),('JEZ57KRC7JO'),('MGR47VPR4SV'),('TMF14MBU5IP'),('LLN52IXU4WM'),('IUE99CBN7DT'),('ZMN68KAL0PH'),('BHF47AAT1AL'),('DGW85PBS9KU'),('TWC82VLG7GD'),('RML42VJR9BH'),('SHH22TBJ1MO'),('VWJ14GBI2EG'),('PLX49TFM0VQ'),('MGP82JBA0CC'),('XMS70JPJ6WU'),('ZSH12NJA0MM'),('STA09MTQ5IJ'),('GYN14NUT4BO'),('ZXX14UPD0SJ'),('NPP93ZIU9WU'),('STK69LJB0SS'),('YZR94QKD5OF'),('OZR42FWC7GZ'),('QUA33DQO0SG'),('TZJ44FRA0IK'),('APH37JAC6KB'),('DMQ19BOQ8ZV'),('ADI68HYS6TG'),('XGE59NDG2ZZ'),('TIU92OGO3NR'),('JKM15FES2CH'),('SFG39PNQ4OD'),('ADC17JCX8GU'),('FEZ89NBF3WG'),('ZNQ50MYR6KD'),('SHZ80CQO8HF'),('LJE28CZA2GP'),('UHH23KWC4BQ'),('TOP10VVE3EF'),('SVE72JGP8EP'),('QGL66GOK8VJ'),('YMN86BNP2DH'),('YTJ95OKF3ET'),('QAG91LLE3OR'),('DUH33CKR9ZF'),('LQV63PBX1AX'),('QFD35XPL1EH'),('KOC44OTH1ZV'),('VUT90QMF2PX'),('JXO02ZLJ8VQ'),('IZD07LTQ7JL'),('KMD66LYC8WS'),('EUV34QVD6VD'),('QSC74UJF5EK'),('WWD35DXY6NR'),('APO93XBB9AB'),('PYZ50AHY3DG'),('DKZ21BCI6ST'),('TUU12MSA2UF'),('HMB34JZL6MA'),('ICO15XMM8GG'),('PVO17IYR3ZY'),('OHA16OEV0BO'),('WFT88HSI2FP'),('KUL71QNY8XP'),('WYD81QJQ3CX'),('HLA21JIT0DD'),('CJN69NFN5WW'),('HEY26ALL0TL'),('NDE79JWK7LZ'),('NSW49EML4KX'),('JXM99FND5TC'),('WMT50AXX2TV'),('EIG88ZRZ6SQ'),('UDD04FTA8II'),('JNK08FWK6IB'),('SMU10XTK1NA'),('VDO94NDF1NP'),('RMS01VGD8WL'),('MHZ33OUX8WF'),('BPF81TNZ1GC'),('FSB70PWA7ZP'),('ZEU10PTQ6CX'),('WCN56PMX2ZJ'),('VTD60PZU4NU'),('LCJ00TDS3MI'),('WQJ98UBQ7GX'),('GMJ40KNH4VZ');

INSERT INTO f (b_id,VALUE) VALUES (96,'FEY70GPR8DP'),(200,'WDE48NSC1SY'),(84,'UKJ55VAA0JN'),(109,'XXM10HHG4DO'),(172,'NXM90BXP3JM'),(107,'JAQ18TYI6VL'),(52,'DVR08YKB3FG'),(107,'XNG42YKO9EI'),(33,'MOY54BMS3LW'),(141,'BCM42WSY3PK'),(81,'IDK52HBH9KQ'),(136,'XZV28OBH7TB'),(121,'RGX95XQY9QA'),(148,'QUU09DPN4DV'),(181,'GOW41XMR8PL'),(247,'IAM29VTH6LD'),(1,'TPQ65CLH3ON'),(285,'NUD69RSF6TB'),(22,'KPZ13KMK8PU'),(52,'VZP64EGW3DP'),(43,'COS89CEP2QV'),(213,'BJI87PGH1CM'),(71,'NLU81BRC6CD'),(258,'QGS08WHC2JH'),(99,'KCL76EHA4RV'),(211,'RIF72URH8RT'),(29,'IRM29VWB2LD'),(130,'FCF31AZQ2GZ'),(276,'ITX84VDT9IP'),(94,'AVX25YXU8IK'),(9,'QHJ36CRB6MY'),(183,'TJN60OEZ5ST'),(71,'HID69EDY0YF'),(197,'PNU62XHY7OV'),(128,'STO29KEV0EL'),(287,'VZB09MZI4WG'),(262,'CGZ13BTK7WW'),(94,'CFP37OSQ5CF'),(238,'GVF55AOZ8UA'),(138,'FHM36KLO4IU'),(289,'LXS65DPM9FX'),(8,'BWP01FIM8OU'),(64,'INK81JLP7KF'),(170,'RMQ05ZMV3YQ'),(191,'UMK60ZXA7CZ'),(227,'PDG86NED0TX'),(229,'QFM75SII9AG'),(133,'IEG49FRW3GO'),(296,'FMU90RGG2NY'),(124,'YQF31FPX3JC'),(300,'XEV15CPT0II'),(11,'PNA12UZY5FA'),(101,'WOH91PNH4VN'),(147,'IRV50AQN0KZ'),(197,'HQK64ZQC6WW'),(276,'TGC38MPB7LQ'),(236,'KBV65OCG9DO'),(26,'NWX36MYQ8BT'),(145,'TZC86WDB5WY'),(165,'JIE85FSO9ID'),(162,'SEU75YIW9XI'),(250,'WEK17FQO4SQ'),(205,'BMB79OOX5WU'),(176,'GHL36FAY5PI'),(189,'UOL35XMI7SD'),(154,'JKN79CMG2QL'),(176,'OBA87RWX5ZL'),(77,'IZV62ANM9HZ'),(269,'PUT49VJJ0GP'),(219,'HLS73WWD4ED'),(221,'VOU82WQH6WH'),(73,'SZQ74AQI8SZ'),(107,'IDE17OKC4PP'),(116,'BDM20JRO7QV'),(226,'PUF23KEL0ST'),(85,'NMN31OJX8LR'),(275,'VXK03OKM1EM'),(10,'HKW92MRJ6WI'),(17,'OEU42MPZ9BJ'),(37,'BWL62AWQ9KT'),(23,'GTQ53IJB3QE'),(126,'JBP90FJP5CI'),(298,'QVU43SSA3EE'),(224,'OBS50IRH3VA'),(148,'CTR69RLU0WG'),(132,'DZO45OJH1WY'),(70,'PMI60GQU8WY'),(2,'VFU72EMH5ZI'),(157,'ATP44TDL9KD'),(54,'YVQ75OML5IL'),(79,'KQH70MHH0DF'),(121,'GJU73LUN8AA'),(178,'CAG82KUX9HQ'),(36,'DUQ53OVX2EV'),(217,'SKF34UHE8PN'),(42,'DQU04QDE9YA'),(183,'AJG57WHI0SG'),(119,'PVB48VYL1YC'),(72,'SOD73HJN4HZ'),(233,'HRA51CVL4ZQ');

INSERT INTO c (VALUE,f_id) VALUES ('ULT07BXR6DM',62),('KNC94SWG3FG',5),('WNS67YQB8LQ',50),('VMT61UHX2BB',36),('DYM04QMN6LN',94),('PHZ99KJA0HI',15),('VUX62HTG0TE',70),('FIA62XJN0XR',96),('QKH76DGK8IQ',87),('GUX15CFC0WP',85),('WIO92UNV8EP',84),('TTA82ONG4UD',50),('KMG80UIS6HA',66),('RGY18ABP6JC',24),('ELN22FTD8PA',60),('STH84CCA1DA',41),('SLK62QSR9CT',6),('JVC70URY1RR',51),('PYT31VFC4JQ',10),('JNT83BFV9VH',76),('MCM55STX4DS',92),('CCP64XOM9PN',46),('HGM18FKI4RY',59),('IZK04GAW7RP',28),('ZRM45ROI6KE',8),('VDA16KJM7RS',74),('CZE00CIC9HJ',22),('ZIO91WFR6GY',93),('FAS17AYW8JL',79),('LEU97WJI3NI',54),('SZH72MLI3XK',77),('YOD64HMZ6IR',63),('ISY03TWN6VV',4),('ZCA02CAD6LO',27),('XND57NCP4IH',62),('NCN30QVT5LC',81),('QUG98EQF6EC',68),('SGS46KDG8IR',87),('DIO32VSQ1SR',79),('ZJJ89YSX4YH',3),('WCU29RGV8UL',26),('ZVB87ZPG8QC',74),('BDL32UPG0AK',41),('GLM82DIB2ND',60),('PMQ72ZZI5SE',79),('HUO09RGP5TC',32),('RYD69CYQ0TQ',41),('NMU30LKV6SV',20),('QSS86XBJ4NC',21),('VHI19QCA9IH',45),('UVI87UWH0QW',69),('NTZ02SLG1RR',74),('SIU87IHT4PD',98),('XYH53FSY1LB',29),('MHC18RNS5YJ',8),('IWV18YDH4PT',45),('GHS28AOO1MO',35),('NKH79IOF4QT',18),('SWJ06YHG6KM',47),('DQU40TQB6BO',97),('DPQ06NQH2IF',52),('KBR77JED8JI',48),('DMC21PVM1DH',85),('GVZ26KUQ7NG',2),('CMN90LQD7CP',25),('JRG69RAA7DC',55),('JAB98OEQ7HY',48),('RLU41XVF7ZE',75),('IEN06UTM0XE',17),('XUV28ADI4HF',63),('CIK42JVM0JN',67),('LLA78WXF8RX',19),('YYI71EGG1LL',94),('DHE60MBF2IB',21),('QOB08LRJ0SZ',19),('VRL49YYQ4UI',92),('DHP84OIR7ES',46),('QZO39KUX5KI',98),('BFW58EUB6VO',30),('NPJ36AIQ4BY',46),('JMM30ERG6LF',40),('LGR44VOQ0AI',77),('JCH43HBZ8WE',33),('LRL29LBX0OM',19),('GHE43CUV2AX',13),('MYY52FPY7ID',72),('KAM48QNA0QT',28),('ZXI80YRH6NO',29),('KYP96BZL1HF',46),('OTD20PPC3HH',40),('YBK29KPI8KT',26),('IFZ75LEG7JT',23),('SDZ84RDJ9VF',91),('VKM88NUK1PR',15),('TXY96NIY8GF',29),('AOI51RLA0PF',55),('EPL01HUQ8FZ',74),('LNC98QFH0CV',22),('ZMR25MHZ0OU',68),('HXK10PQV1WF',69);

INSERT INTO a (VALUE,c_id) VALUES ('TRZ36HWF8LZ',9),('AVJ28MWH8JG',32),('UKJ36LWN0VT',48),('VQX53QZN1NV',75),('MBY86VHP3CF',91),('HOQ45CCT5CF',52),('HZK07OPP5WM',97),('JGU00LSD6AD',90),('HST64SXB2ZF',21),('LOR69BQX4XY',32),('QXH09WXQ9GG',82),('LNX52AHH8IV',67),('JRE67OSN5AZ',6),('YJN52NMZ9ZN',71),('DSK96MEC2ZL',93),('LGV76DZK1NO',59),('YEK08IOM6AD',78),('PNP59RPG6EM',49),('INU97PDH4SC',34),('ZEC98CCR0TX',82),('TYS68DCS4JE',41),('DWF71GEJ8ST',21),('GPL63UII6UX',66),('VLI95VJG7XJ',16),('QFU52GCO9WM',15),('PQG44REG2QA',22),('NFP78CHG9HB',30),('URN71IWJ9VV',24),('QUC52FED4UW',9),('JZI78QYD2VI',25),('FHI80CJK3GM',64),('XEZ16AOW6WJ',60),('HZB91FCX8UZ',36),('GEQ34VOO9EI',75),('FJV21PIN9GC',86),('QPY46NPY6YH',98),('IGN43XKC6ZO',83),('WVC75EPP4EH',50),('RKI47MJK1KM',86),('QGG67ZGK8PC',66),('JSC62GZT5WI',32),('FEO46NYW2WM',11),('XPW53QXR8FT',69),('WIU61KKU5ZS',39),('JFW92CUW4IB',86),('WFY03XPX5GZ',68),('JLF13VBS7ZT',74),('VDX17OBQ5TM',2),('CYL43LBR4JQ',57),('ROF36KME1NS',24),('CRC15WNQ5UZ',36),('PAQ15CJM6LE',76),('AIH68FZC8CV',87),('KSI96XZQ1TV',12),('LYM37ZQX0TP',86),('RBS39AAA6CP',25),('PQH33HIW5BV',28),('WRQ34KBY2HB',78),('BFY63XUC3QK',38),('GXX97THW0QJ',77),('UFV60UXQ0HF',62),('PCY75BAE2FU',93),('JZL72OEL4YL',73),('VLX74CFX1FQ',72),('ONZ70YHF5LM',93),('TOV01HSM9HR',13),('HUP37DTV0NR',14),('ERI77BFY9FZ',73),('MLO25GUD5IY',48),('PJX36UAN9EP',35),('WJS58QIH8LE',13),('WSO20RRQ9GM',35),('TCN42EMH9WU',2),('CDV64LHI0ZW',59),('ZHG22HME7HC',81),('HAC82TNA7CX',86),('MVZ81EZK1OX',16),('LUG41IYU1ET',64),('HQE51WYF5XI',13),('FQR78JYB7VQ',90),('FIU15QAB3TD',97),('NCS93ZRZ4BN',63),('RAJ28SWY8VA',83),('YXD48GJK9LQ',50),('AIK93SXO2KI',86),('XDE46UAY6WF',37),('EZO15LRW2KS',18),('OOU88GBG0PO',15),('AJT05MOT4OK',3),('PNL31LEM7DA',76),('LQW07SYP4RZ',74),('GYA95CNT1FR',74),('BJB27OUJ8HM',50),('EQB84HMU9AO',94),('CRZ92BNJ5ML',63),('HLR31TCU3EA',62),('ZLP68RZX8BD',33),('NXH82OIS0AR',33),('PRK84CNK4AT',40),('PKJ50LMI2JH',51);
INSERT INTO a (VALUE,c_id) VALUES ('BGU54EZG3TJ',10),('CMO15VZQ7AV',35),('ZNW13EFL2FB',53),('AKK19SJP5HJ',49),('ETP50MHV2HK',37),('UFT27HRN0NA',71),('IYE00UAI0GU',78),('GAI31YVC0ZW',48),('ZCG87DZN2OX',26),('LKO16TOL3VA',84),('ATU15EUP5BU',20),('YLC41OOC0TS',11),('NHE70SPU0EY',29),('HYM89AHA8XI',63),('WHL40HVQ5UB',80),('HIC02TTM2XZ',37),('VRO55TKT8XS',63),('PDR98WUG9OW',66),('DEJ71ZEN3VY',62),('QUL82JWY2YL',17),('FYS81AUD4JW',47),('FDR52KVO1UT',34),('VNV42IGB7UB',89),('PYH53NXP8HX',34),('HYF13ICM4XR',45),('OMD46ZNX1OJ',29),('VKK58IXF9XN',82),('PII91SGT0PM',63),('GZO25ZWT8JV',18),('XEC48CAK7WQ',9),('BJQ43PEZ8RX',55),('HOW58NES7VQ',49),('KRC79UGN0YV',29),('TPI27VOF2VY',95),('CYF57AJD9YE',55),('TWS14RJK2ED',66),('FBF40YWL3MD',13),('QHK79QYP3LR',79),('XTC18LIA6TX',75),('NNB36PKA3EB',39),('DAT59KNE7JW',86),('WJJ35JEI6SP',90),('UYU60CQQ4EX',43),('HVP98KXL4EQ',70),('YSL01KRZ6VY',42),('EZH63IKM3WQ',18),('DMT25QVW3IH',5),('JPY17XCB4GZ',79),('BND46YCI0EF',90),('ZZQ92PUG9TS',57),('KPF89GNG8IL',32),('AVM58SEM3ZC',91),('IJW86STF2PP',69),('NZV41DAT2RK',50),('IXO54SIZ8AU',92),('NCJ79MPW1ZC',3),('PWY77CAR1XS',22),('HJX48XFR1YT',26),('CCB64XVA3JJ',98),('HHK59DSD3QV',21),('WHT48PAP2TA',47),('CTQ93FWY1PK',50),('RYQ82QBP2BE',14),('ECI93JIE7PR',59),('VCF76JFD0CG',46),('KZX97USM8PT',21),('VLQ05UTM4GC',57),('LOA71BSM8YX',88),('FKP27GTT7HV',84),('HMS77MPR4UV',72),('NPJ18NPJ4SH',63),('FNL55CXY7HH',87),('WGO63XJK9EF',73),('YXO79IGD5ID',37),('KXI41DTG9QQ',61),('WUE48LMV4NH',64),('CIZ95UQA1UR',62),('MIX27UQF6OT',66),('QYM34YDZ3KT',18),('XKK93FND8AI',88),('FUT63GOH3GX',67),('WFT09PAL4AG',6),('ABD86OVL9GV',49),('ZGY16BCD5YD',21),('GNP94QYQ7QI',41),('LDF17GST2CS',92),('RXL03WFW5EM',17),('BVW34SYJ2QY',70),('TBZ45EWV3KA',28),('UKS83ZVP2DH',94),('LAO99CPB2FK',34),('VVE87XPP4HW',93),('DLC21YPO9WH',46),('DGU89VNY5KR',91),('XNF64LGP0HI',74),('KEL84CGD1FK',57),('XVY51AUZ7LJ',62),('QTW57ZOP1XG',65),('ORK67WMY3DE',84),('MHV56OCQ2CR',22);

INSERT INTO b (VALUE,e_id,c_id) VALUES ('CKU74ZVQ6HA',95,34),('LWK70MUB4NN',62,66),('UUK34BCJ1WG',59,69),('JEU06HUU2LW',64,53),('RXK62FCV1DD',56,41),('LXS90XUM7KB',49,51),('VLT24ERG1PU',55,24),('KGL48CPJ4DA',83,91),('DQY64JNT4UH',59,62),('SER00YPG2MI',81,53),('HTT62QWW1CA',97,99),('NVT31VCE4FA',69,18),('YUD88XTV1TQ',52,18),('IHA57XFN9LI',93,67),('THA65VXU2OL',62,43),('CAN26XBT7WM',53,86),('DLM10SIW3BV',3,35),('GKU98OWE1PB',29,93),('UCB64QFG6AT',61,8),('UNG15PUX7TS',36,11),('SFU76MCO6UU',59,87),('ZYI20TYV1LY',92,55),('ZCO08AIV5WG',35,2),('FXY95PWF4FZ',52,33),('SRG88SVA3ZX',90,6),('VYC42TLL5OO',43,82),('ESM25FWP6AH',11,28),('TDP08DJP6WI',75,59),('HND22EIK2KO',47,29),('AOV36JHN1ZT',46,93),('BFL04LMJ6IJ',66,1),('UEM08ZKJ9XP',96,81),('CGS49RPF0EH',50,49),('KTE25CDA2NN',93,4),('AKU70DSO3BY',83,38),('UXX17MNA2CV',7,27),('RRY08QXM3HN',29,8),('POF05WJF9KY',10,55),('VAX57XAQ2PD',33,83),('LXR12QPG1RB',67,78),('QWH53GFM4IP',72,65),('PJV00XFX7QP',66,9),('FJW07SNN7TD',88,18),('ZLH69SZI7GM',34,17),('VRJ02LGA8EI',8,55),('RKN15WTX1SB',6,9),('JQR89AWD4PJ',7,25),('SSW10PKD4DX',24,65),('YDV83KEG9MQ',55,35),('ROD38YFL5OG',83,54),('UBS38NGW5HT',30,24),('ORP32RUH4LI',77,35),('LFC21SCG3XN',71,12),('KSW47HNN7KL',38,38),('RVZ09IUC4CP',37,17),('IAO40WHZ7YQ',72,38),('XFX42DYZ9SG',7,5),('AOX88GOH1GJ',77,55),('WVK66WOW6BX',84,37),('FRT33NYV3GX',98,2),('JOA56OBF2BE',47,87),('RDS18NNF7OZ',92,50),('UUR55CFP3XB',66,48),('UAC51YGZ1ZK',45,68),('MVO28JFI2ZG',66,34),('NZV74GOQ0VN',55,20),('UUT90BPL1AT',71,62),('EMG99CJO5CW',22,24),('OCO18RFU5UI',74,29),('LOC38FBL4UW',3,42),('JLM00YWR8YU',19,54),('VQQ99LHT6RR',78,92),('IQZ81YRN5XM',21,85),('VXD40LDO6CK',66,63),('DCP80DEI3HA',14,61),('PJF65HLE1KU',59,14),('FSY62KDX2PF',44,75),('ONC11TBD0MM',75,7),('HAA81XUL7DZ',50,30),('MBK10RZA8WP',35,96),('ZHF15YDJ6UY',58,25),('SQJ41STT6RR',41,2),('GET27FEI5BJ',60,27),('QDA52APO8BF',87,45),('EPO23VDM6XJ',23,1),('ZVZ46VZO0YP',95,78),('LNJ40LTQ4PO',56,27),('PDQ48KIR2SQ',84,47),('KAM97NJN7JM',57,60),('IPL19JWV7PN',49,78),('EYQ22MFT1CE',73,63),('PFS60ZTV1QJ',64,4),('DSY91KMM7CS',44,26),('ICN62CMU5OP',9,1),('LHS98YQW3DO',38,70),('SSM74BYZ9XC',24,6),('CUK74YZS1EL',39,62),('FEB67VFT8KZ',77,95),('HZH73ZPU9SX',63,4),('KQV62GMC4UN',59,69);
INSERT INTO b (VALUE,e_id,c_id) VALUES ('SEO87ATM9NE',80,54),('VWE95HXZ8KA',83,56),('VBA86TZN6WR',38,42),('GHS87WMH6XQ',46,96),('JYM58EMM5IM',57,87),('NVV69TGU8LW',64,75),('FGN06ADH9OP',19,69),('SVN79GEZ2HA',35,93),('SNW21NAV7CF',71,40),('EPE47FWQ6BI',1,51),('QWN67DCF5FK',29,53),('XCV55DYW4TP',21,59),('XAN56SXH0OG',68,89),('XCQ53HQO6PL',80,72),('PVN39KZZ7UG',56,56),('EKM36PVV3HH',3,51),('SFK14JGY8NK',92,88),('SKY95DNQ0TS',56,45),('OOU58MLE9HO',69,60),('NLO11SHS7MT',2,66),('QDA46BSE2IS',67,30),('BZQ16WQZ5OL',100,57),('DZE94LMV4ST',48,78),('BRN27BBD5EK',59,43),('FZR97QPR5MA',51,28),('BDS23LEJ2DI',74,14),('FDO44EJU4LL',15,43),('QQT83QJG2MJ',69,32),('XPF14SRT6PH',89,2),('CCY34KMT5ZN',30,58),('VVB59NXQ4QZ',15,34),('HIC72NGT9XY',19,95),('NFG68ZXH1VV',67,40),('DAW69XYI9DD',5,74),('SLG42KES0HX',6,63),('PPA85HUE7LN',26,6),('WES58XUV1FT',2,41),('SCV99XNJ6IS',44,31),('YMY22NTN9GY',70,99),('CLS69ZKH5GV',6,10),('QCA86WYI3SP',99,26),('AAR53ADM5RU',21,87),('XIZ55GBM8VP',15,47),('PHT27PCC5YP',63,56),('AVS11KFB4YT',34,36),('AVL58ZTJ3FY',48,39),('MNO40XWU8FN',32,98),('APG17LZJ7ZQ',49,48),('ZLZ52XJT2AT',53,48),('ZWR53VAZ6HB',63,20),('RKO16VOJ6AY',52,94),('JDE56APL8PQ',29,50),('EWT04OJW4XG',88,34),('DHR63UAO4RU',68,33),('IDC37QGA8WK',81,72),('ULQ77TIF0HX',21,73),('VKC71YEP8KR',2,69),('HLR73JPW8UB',41,75),('KOH84RNN1EG',48,73),('UBX79DJI4CK',27,86),('ABG47UGB4GK',25,19),('EBI05KBD5AK',19,54),('MUZ78IES6KG',51,100),('CVP39DJP1DR',26,54),('ZJE97GAE1XJ',27,37),('YAO76BCJ1QI',60,24),('APF32LIK0JH',97,40),('ERN03NTH0FO',85,58),('ZPW24HUE2WC',89,95),('THC95AVW6PP',100,4),('PIE35ICO0CS',61,47),('LII97CMS7LF',43,19),('OVH30MHQ4IF',20,29),('SEA95MKC1OA',52,94),('SSW23DIX8OG',34,57),('NFJ34QNB8JD',37,15),('RYP42ZJC5GS',11,70),('KSH34QQM5TS',14,95),('NWQ86GVY4NS',9,43),('IES25RFO2SV',13,43),('QTE25OML4UY',43,93),('WDG48BLD6TA',95,10),('TIM98XCQ8UC',75,17),('ENT32OLV9CS',45,73),('YSE98FGW7ZB',85,5),('TWX35UNL4VA',20,83),('BGB34WUX3PO',67,36),('QGN23ZMQ3OE',97,71),('KHO81IAD7EP',10,90),('HRV69RJB9MW',59,73),('JDG07ZBC0JU',22,50),('RYU08BVJ5CX',51,24),('PTQ53GYI2UP',85,76),('GFA30MZN9IF',57,64),('IKH57SSI7OF',71,90),('NJD29ETN2SS',15,85),('QTM88EKJ4EN',65,72),('NVO28HHF2ME',35,48),('XZZ26RVP2QM',99,48),('BFZ86ESM9DY',28,99);
INSERT INTO b (VALUE,e_id,c_id) VALUES ('PUU91KBZ1GG',29,43),('OCV01GXO0MT',68,87),('EBL97XKG4ME',61,44),('MXK86SIE7GU',62,99),('BJR65WOX4BR',29,37),('QOE23NGO4ZM',73,99),('ODI72SVV3DK',36,49),('RIV87UAR9XA',44,73),('FRR18LCK3LZ',43,92),('CRY24BQT0IN',11,43),('YTY11ERJ6FY',71,60),('HWN85PUN6LL',35,53),('OZT90RAB7XF',98,88),('XDA98CXR9XG',41,7),('FZQ64TAG3XP',97,58),('PAA22TIQ1UE',93,19),('HTR48JNW0ZT',65,82),('WKQ18DEZ5WX',21,18),('WEB75FVC8JM',17,96),('YNA89ZCQ4YY',65,65),('DNG88ZPA2JQ',4,55),('MHT71OQP0PX',33,26),('NMG20ZKI0TS',45,20),('VIL52IPS8WF',40,26),('HCA29BWX9SX',40,75),('TQP16PVN4BF',50,22),('SHA41ODS9DC',49,4),('BKS30PAE1LP',58,62),('YYP48GKA6AT',58,24),('NCC45RSE6PK',60,44),('CEF91UQU3SH',82,31),('FJK33UJA5OS',29,17),('WYR70JLZ6VV',75,76),('WVJ37HNY1PP',59,82),('GAC98FZJ5WD',26,67),('GOH12VYS1HZ',99,61),('FPV23EVK8IG',13,55),('QNJ73MLA2LZ',43,36),('MBE45XSC7VI',82,21),('GCP06FLY5HK',67,18),('RLA41RSJ7BC',45,25),('QDO13LGJ3KA',4,11),('JPB49EDZ8EV',50,26),('URF30VOU2TI',49,74),('HCY77YPN4OO',65,35),('SFA37KGW6VD',46,46),('JFZ28QTF4IU',31,63),('QTZ63KTN9LK',30,37),('XQQ69YGV1CN',77,78),('RGH43KRT6GP',62,54),('SPA00BCA0MH',56,15),('BLU48MKG6WQ',45,17),('OYV07GHS6QP',15,20),('ADT05BGQ6LC',95,14),('OUY89MSP6YR',56,97),('OBD92TRI2BM',77,12),('SUR05SXM0BR',8,30),('ARU76NRZ7LV',19,35),('KDO74LOX1UT',41,56),('YHS82ECX5NR',59,96),('GKN68ZLH6YZ',65,66),('TEU18XBC8EW',20,1),('CLW19TYC4LH',73,75),('VOU55XPM0OE',14,81),('ZGT96MYI7GR',31,86),('UAI29LPR9ZV',7,39),('VFY27XZS6NU',15,5),('DIS88FLX0ZJ',23,44),('NZB03NQU2TZ',53,98),('AXP11PTS0KE',21,80),('TGS64PTN9RP',28,32),('SWZ23WLU7RC',64,19),('XHT18KIJ1ZH',9,59),('BYK00KXI1FP',7,24),('RZY86MIF0DM',90,28),('WKU78QPR1ZM',93,20),('PAF80ICP1JW',66,1),('QQA33KFG6DN',42,93),('CAG86ACZ3JU',29,31),('YKW61FDV7DZ',45,64),('HVA60GGK0RJ',27,5),('EXS77RVH2ZK',84,75),('UVO56HEI7MG',82,69),('NYC30ETH9SH',72,59),('IBG18EJW7YA',59,47),('WOA07AIX9YI',66,15),('GUM34DLC6LP',52,97),('MBS37IQC1TL',61,91),('NPO37BSI1SG',72,97),('QEP21WCU1TV',80,12),('PHM94VWE4FP',34,90),('TGH13HDU6DX',56,35),('LNT71PLJ0UE',59,97),('YFC60DIJ3EI',16,13),('FKR78JFB3UG',45,50),('GJA69JNF2FD',68,73),('KCM65HVE6SE',8,68),('ZQC00QWR1SF',94,72),('CMO51IXR0LH',6,40),('VDQ50CHT6EP',81,43);

INSERT INTO d (value,b_id,c_id) VALUES ('TFF25VYW9EO',108,48),('CIO13ROB0VD',168,60),('ZXJ72HDJ1FV',209,96),('BOR64ZAB2AS',138,97),('WTJ23BKA6XB',111,23),('OLH66LPX0JR',61,26),('YMG95VYY1UM',253,32),('OAO17XFB2CL',143,42),('ACU48CTF8TL',147,8),('SUW65JUW1FP',236,6),('IWF79BVI8SS',132,84),('BFW38HYP3FU',95,32),('BJB40UWR0FW',274,79),('GZD19TVO1MW',219,4),('NFY91MFA7JH',163,88),('GYS14KDI5CA',76,57),('IGS04QFG4NL',117,98),('UZN71UYD4CJ',120,7),('MJO37KEC7ZQ',158,32),('NZW33QKY1SY',147,14),('VQY61IRX4TO',205,17),('FOG25FXJ6RN',199,5),('AAV32IMM6MC',185,50),('HWY55HAW0UC',242,71),('UTD76CSP1LN',42,31),('IKQ33FOL4KB',52,25),('RLQ32NTI2VF',187,45),('EDD96FUQ5RG',110,39),('INF57RXJ0FE',294,94),('DFO36FPT4PU',57,48),('RZZ64JOH7FA',12,79),('MPW46AQB9TQ',209,12),('UNW73LBF1CA',146,29),('KYQ49UUU3NX',81,78),('SOP55PAG2SE',31,10),('KCG17QBT2SQ',236,72),('KIP09ULR9IU',33,58),('JWZ04KUG0WZ',33,52),('XAY15HGX4ZP',88,24),('YUK39NAS3GQ',144,55),('BJR06RRH6JU',226,58),('DNW92DVB2NP',273,37),('HFL80VOB1SD',90,50),('BLW01QUT9BT',250,17),('FGU13DER2YN',74,14),('EDD08GVN4FP',47,78),('BEI23AGK3XD',287,88),('PXH84YAU9FG',175,60),('IMW69MYE1FY',242,61),('JUL82PDP2GA',282,16),('JOL09IXF4VL',33,20),('SXR64PJQ8DI',235,2),('ALI91ZBR0BU',256,80),('YOR35PSS6ZM',241,89),('KOZ76RUE5KG',89,91),('BON77NCF2VQ',77,12),('JMD10PZV5KW',106,24),('UEN50EYM9XE',256,13),('RMH50TTJ4AA',113,56),('DAH28ASM0RE',76,45),('HXM84GKD6KO',144,96),('HPF02JHR1KW',157,38),('YBZ52TIU1PQ',50,7),('ZSW63HBA5TB',129,78),('KSO29XXQ1OJ',215,51),('UBX83RLT4MM',113,65),('ZMD06DHI9TD',173,75),('TYE22QIL7KG',46,39),('RDL70TUP5PD',70,70),('MAK97VBE4VL',55,87),('WNI44MVA9GU',29,67),('FHR10CQF5PM',299,2),('ZNN87IFR8GT',193,26),('LPG60HVP1ZW',106,88),('XSC91SCL4DU',110,42),('WXS84DEH7ZJ',88,15),('OIK54QLS7YC',155,19),('KEV90LLE3ZV',244,62),('SEO65YMJ0WB',163,55),('BEZ18UZJ2WX',89,30),('GSD65IUJ0YT',103,33),('GJT50SFO6JG',248,25),('LMV99KRO6LI',130,61),('ZMS68ZVQ3FP',190,30),('RRN80LKG3IW',96,25),('ZAI01VLG4RR',211,55),('UXC72OWC6LS',61,8),('PUH53TCN0NZ',194,69),('VJO33IOG2WU',190,41),('ACS19OXV2MW',74,96),('YTV12QYU3NT',196,50),('DHT72VUR6ES',140,30),('NDP51DSH5GY',92,68),('SCS52UWJ5GS',52,40),('HSX20ELZ3AU',281,1),('AAM18VPR3KK',77,78),('IQG99DIT2DP',224,19),('NHW63PDU4TB',202,19),('ODE30QRK5ZC',207,15),('HWO34AIF5JU',49,69);