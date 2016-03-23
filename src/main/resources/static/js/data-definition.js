app.factory("neighbours", function() {
   return {
      "fir_component": ["fir_value"],
      "fir_food": ["fir_value", "fir_ingredients"],
      "fir_ingredients": ["fir_food"],
      "fir_value": ["fir_component", "fir_food"]
   };
});

app.factory("dataDefinition", function() {
   return {
      "fc_celiac_members": {
         "CODE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "FIRST_NAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LAST_NAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CREATED": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MODIFIED": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_diet": {
         "DIETID": {
            "type": "int(10) unsigned",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "DIETNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DIETDESC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DENGDESC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AUTHORID": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_ematerials": {
         "EMID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "EDUMAT": {
            "type": "blob",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "EMENG": {
            "type": "blob",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TRID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_exchangesystem": {
         "FGID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "COMPID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "MIN": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MAXL": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MAXU": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "UNIT": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SOURCEID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_ffq_pictures": {
         "StepId": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "0",
            "extra": ""
         },
         "FoodName": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "Size": {
            "type": "varchar(32)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "FFQ_ID": {
            "type": "int(11)",
            "notNull": false,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "Picture": {
            "type": "longblob",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Weight": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightDesc": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Ts": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_ffq_steps": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "FFQ_ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "0",
            "extra": ""
         },
         "Question": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "StepNumber": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": "1",
            "extra": ""
         },
         "Type": {
            "type": "varchar(50)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Instructions": {
            "type": "text",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AllowSkipTo": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FoodCodes": {
            "type": "text",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightS": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightTextS": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightM": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightTextM": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightL": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightTextL": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightXL": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightTextXL": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightXXL": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightTextXXL": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightXXXL": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WeightTextXXXL": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency1": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency2": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency3": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency4": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency5": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency6": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency7": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency8": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency9": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Frequency10": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_ffq_warnings": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "0",
            "extra": ""
         },
         "Result": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Description": {
            "type": "text",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Url": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_ffqs": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "Name": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ContentId": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Recurrence": {
            "type": "varchar(50)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "OnlyMediumSize": {
            "type": "tinyint(4)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "AllSizes": {
            "type": "tinyint(4)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "RemarksLabel": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Created": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "Modified": {
            "type": "timestamp",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": "on update CURRENT_TIMESTAMP"
         }
      },
      "fc_foodalternatives": {
         "ID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "DIETID": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ORIGFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "ALTFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AFAMFAC": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AUTHORID": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_foodgroup": {
         "FGCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FGENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FGDESC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FGENGDESC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_foodprices": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "FOODID": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "PRICE": {
            "type": "decimal(15,2)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CURRENCY": {
            "type": "varchar(10)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "STOREID": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WEIGHT": {
            "type": "decimal(15,2)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "UNIT": {
            "type": "varchar(50)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "USERID": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_foodquestionnaire": {
         "FQCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "GSAMIN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GSAMAX": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GRADEA": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GSBMIN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GSBMAX": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GRADEB": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GSCMIN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GSCMAX": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GRADEC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AUTHID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_foodstores": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "NAME": {
            "type": "varchar(50)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ACTIVE": {
            "type": "tinyint(4)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "USERID": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_foodsubgroup": {
         "FSGCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FSGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FSGENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FGCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDBFID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDSFKID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDUSDAID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDINDID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_fqquestions": {
         "FQCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "QCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "QUES": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "INSTR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "COMMENT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS1": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS1": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS2": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS2": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS3": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS3": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS4": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS4": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS5": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS5": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS6": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS6": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS7": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS7": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS8": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS8": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS9": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS9": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANS10": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "POINTS10": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PMAX": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ORDER": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_gastro": {
         "ID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "NAME": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ENGNAME": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CREATED": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MODIFIED": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_groupofpeople": {
         "GPID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "GPNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GPENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GPDESC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GPENGDESC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SEX": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "MIN": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MAX": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "UNIT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "WEIGHT": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "HEIGHT": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SOURCEID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_gs1": {
         "FOODID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "GS1": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "DISTRIB": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "COUNTRY": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PRODUCER": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_guidelines": {
         "GPID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "COMPID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FGID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "MEALCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "VALUE": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MIN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MAX": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "UNIT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "VALTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SOURCEID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_householdmeasure": {
         "HMCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "HMNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "HMENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_kulinarika": {
         "ID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "TITLE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_lllclassroom": {
         "USERID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "LLLMID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "DATE": {
            "type": "datetime",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GRADE": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_lllmodul": {
         "LLLMID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "LLLMNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LLLMC": {
            "type": "text",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TRID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_llltest": {
         "LLLMID": {
            "type": "int(11)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "LLLMQ": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "LLLMAA": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LLLMAB": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LLLMAC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fc_mapping": {
         "FDNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDBFID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FDSFKID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FDUSDAID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FDINDID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_mappingind": {
         "FDNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDBFID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDSFKID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDUSDAID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDINDID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_meal": {
         "MEALCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "MEALNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MENGNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FROM": {
            "type": "time",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TO": {
            "type": "time",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GROUP": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         }
      },
      "fc_menucls": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "NAME": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_menutags": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "CLSID": {
            "type": "int(10)",
            "notNull": false,
            "key": "MUL",
            "defaultValue": "0",
            "extra": ""
         },
         "NAME": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         }
      },
      "fc_municipality": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "TITLE": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         }
      },
      "fc_pal": {
         "PAID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "PAG": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PADESC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PAENGDESC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SEX": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PAL": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "METS": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "UNIT": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SOURCEID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TYPE": {
            "type": "char(10)",
            "notNull": false,
            "key": "",
            "defaultValue": "A",
            "extra": ""
         }
      },
      "fc_percentile": {
         "SEX": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "MONTH": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "TYPE": {
            "type": "varchar(50)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "L": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "M": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "S": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P1": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P3": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P5": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P10": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P15": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P25": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P50": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P75": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P85": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P90": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P95": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P97": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "P99": {
            "type": "double",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_recall_categories": {
         "IVZFDCD": {
            "type": "varchar(35)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FGCD": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "IVZFDNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ENGFDNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "LEVEL": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FOODEX1": {
            "type": "varchar(35)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FOODEX2": {
            "type": "varchar(35)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ORIGFDCD": {
            "type": "varchar(35)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_recall_category_pictureweight": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "IVZFDCD": {
            "type": "varchar(35)",
            "notNull": false,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "PICTURE": {
            "type": "longblob",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WEIGHT": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_subkulinarika": {
         "ID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "CATID": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TITLE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CLASSIFIER": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_weights": {
         "ORIGFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "HMCD": {
            "type": "int(11) unsigned",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AMOUNT": {
            "type": "double",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "IMAGE": {
            "type": "blob",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fc_yieldfactors": {
         "ID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "FOODID": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PREPMET": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TMIN": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TMAX": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YIELD": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YNM": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YENGNM": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "RETREF": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARK": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fir_component": {
         "ECOMPID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "ORIGCPCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ORIGCPNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ENGCPNAM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "OPTIJED": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "IZVOR": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DACH": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DACH2": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CPID_USDA": {
            "type": "varchar(3)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CPDESC": {
            "type": "longtext",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CPMUCH": {
            "type": "longtext",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CPLESS": {
            "type": "longtext",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CPUNITH": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CPUNITP": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fir_food": {
         "ORIGFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "ORIGFDNM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "ENGFDNAM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SCINAM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CDXFDSTD": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GS1": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ENR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "INS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ORIGGPCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FSGCD": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PRODTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CDXFDADD": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CDXFDFD": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CDXCONT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FAOFBS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CIAA": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "EC2": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FOODSRCE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PARTPLAN": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PHYSTATE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "HEATREAT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "COOKMETH": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TREATAPP": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PRESMETH": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PACKMED": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "CONTWRPG": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FDCTSRFC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "LBLCLAIM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AREAORIG": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AREAPROC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "AREACONS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PRODENVIR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AGRICOND": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GROWCOND": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "MATURITY": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GENMOD": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CUISINE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ADJUNCT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "INGLIST": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FINLPREP": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SERVSIZE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SERVPACK": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SERVSUGG": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PREPSTATE": {
            "type": "tinyint(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YIELD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ALLERGEN": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "DIETCLAIM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "EDPORTNAT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "WASTENAT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "COLOR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GENIMAGE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SPCIMAGE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PRODUCER": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "DISTRIB": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SOURCEID": {
            "type": "varchar(25)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "COUNTRY": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DIETID": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GASTROID": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FOODEX2": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "KULINARIKA_CAT": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "KULINARIKA_SUBCAT": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ACTIVE": {
            "type": "char(1)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fir_food_new": {
         "ORIGFDCD": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "SLO": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ENG": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SCI": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "GROUP": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "VIS": {
            "type": "varchar(50)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fir_foodnames": {
         "ORIGFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FNAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "LANG": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PREF": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FNREF": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_ingredients": {
         "RECID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "FOODID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "INGNAM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "INGAMOUNT": {
            "type": "decimal(15,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "INGUNIT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "HMID": {
            "type": "int(3)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "RANK": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "EDIBLE": {
            "type": "tinyint(3)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "WHOLE_WHEAT": {
            "type": "tinyint(3)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "SOAK": {
            "type": "tinyint(3)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_method": {
         "METHID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "METHNAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ORGMNAM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "OFFICIAL": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GENDESC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "METHREF": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "EXTRACT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SEPARAT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "IDENT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "DETECT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "QUANT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "OMETHKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ADDDESC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ANDETAIL": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ACCURACY": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "APPLICABILITY": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PRECISION": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REPEAT": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REPRODUC": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "RECOVERY": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SELECTIV": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SENSITIV": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SPECIFIC": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_qualityassessment": {
         "VALUEID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "QAID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "QDESC": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "QREF": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "QFOOD": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QCOMP": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QSAMP": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QNUMB": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QHAND": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QMETH": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QPERFORM": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QMATCH": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QREPRESEN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QIEUROFIR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CCEUROFIR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_recipe": {
         "ORIGFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "RECID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "RECREF": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "RECPROC": {
            "type": "longtext",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YIELDWAT": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AUTHOR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YIELDFAT": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "YIELDALC": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "RETREF": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "IS_DRAINED": {
            "type": "tinyint(4)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_recipephoto": {
         "ID": {
            "type": "int(10)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "RECID": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "MIMETYPE": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FILESIZE": {
            "type": "int(10)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATA": {
            "type": "longblob",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEGEN": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEREV": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fir_reference": {
         "SOURCEID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "SRCODE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ACQTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "REFTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CITATION": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "TITLE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "AUTHORS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PUBDATE": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "VERSION": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ORIGLANG": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ISBN": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FSTEDDAT": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "EDNR": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "NRPAGES": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "BKTITLE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "EDITORS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PAGES": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LGJRNAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ABJRNAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ISSN": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "VOLUME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ISSUE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SERINAME": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SERINR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "RPRTITLE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FILEFRMT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "WWW": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "MEDIUM": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "OS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "MEDIA": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "VALID": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_retentionfactors": {
         "ID": {
            "type": "int(11)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "FOODID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "COMPID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "PREPMET": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "TMIN": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "TMAX": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "BREADED": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "PART": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "WHOLE": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SOAK": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "FAT": {
            "type": "varchar(1)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "RETFACT": {
            "type": "decimal(10,2)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "RETREF": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "REMARK": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_sample": {
         "SAMPID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "SAMPREF": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "REASON": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SAMPSRAT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "PLCECOLL": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "DATSAMPL": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SPLEUNSIZE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SPLEUNNUM": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "COMPOSITE": {
            "type": "tinyint(1)",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SPLEHANDL": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LABARRIV": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "LABSTORE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fir_thesauri": {
         "ID": {
            "type": "int(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": "auto_increment"
         },
         "CODE": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": null,
            "extra": ""
         },
         "DESCRIPTOR": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SCOPEN": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ADDINFO": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "SYNONYMS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "RELTERMS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      },
      "fir_value": {
         "ORIGFDCD": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "COMPID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": null,
            "extra": ""
         },
         "VALUEID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "SELVAL": {
            "type": "double",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "UNIT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "MATRIX": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "VALTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "ACQTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "SOURCEID": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "MUL",
            "defaultValue": "",
            "extra": ""
         },
         "DATEGEN": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DATEEVAL": {
            "type": "date",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "QAID": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "N": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "ANPORT": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "NOANPORT": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MEAN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MEDIAN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MIN": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "MAX": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "STDV": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "STERR": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "METHTYPE": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "METHIND": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "METHPAR": {
            "type": "decimal(10,5)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "METHID": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "REMARKS": {
            "type": "varchar(255)",
            "notNull": true,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "fitbit_user": {
         "FITBIT_ID": {
            "type": "varchar(25)",
            "notNull": true,
            "key": "PRI",
            "defaultValue": "",
            "extra": ""
         },
         "ABOUT_ME": {
            "type": "text",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "AGE": {
            "type": "int(11)",
            "notNull": false,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "AVATAR": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "AVERAGE_DAILTY_STEPS": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": "0.00000",
            "extra": ""
         },
         "DATE_OF_BIRTH": {
            "type": "datetime",
            "notNull": false,
            "key": "",
            "defaultValue": null,
            "extra": ""
         },
         "DISPLAY_NAME": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "FULL_NAME": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "DISTANCE_UNIT": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GENDER": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "GLUCOSE_UNIT": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "HEIGHT": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": "0.00000",
            "extra": ""
         },
         "HEIGHT_UNIT": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "LOCALE": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "WEIGHT": {
            "type": "decimal(10,5)",
            "notNull": false,
            "key": "",
            "defaultValue": "0.00000",
            "extra": ""
         },
         "WEIGHT_UNIT": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "TIMEZONE": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "CITY": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         },
         "COUNTRY": {
            "type": "varchar(255)",
            "notNull": false,
            "key": "",
            "defaultValue": "",
            "extra": ""
         }
      },
      "test": {
         "X": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": "0",
            "extra": ""
         },
         "Y": {
            "type": "int(11)",
            "notNull": true,
            "key": "",
            "defaultValue": null,
            "extra": ""
         }
      }
   }
});
