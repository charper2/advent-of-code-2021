package charper.advent21.day7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class DaySeven {
    private static int[] POSITIONS = {1101,1,29,67,1102,0,1,65,1008,65,35,66,1005,66,28,1,67,65,20,4,0,1001,65,1,65,1106,0,8,99,35,67,101,99,105,32,110,39,101,115,116,32,112,97,115,32,117,110,101,32,105,110,116,99,111,100,101,32,112,114,111,103,114,97,109,10,928,1690,960,74,66,40,867,580,20,226,21,10,230,23,532,443,260,31,10,387,1050,216,85,307,38,1132,1289,98,1228,20,908,1740,393,321,18,26,389,1002,1347,307,1483,1108,60,1116,1395,56,98,652,745,511,107,806,801,881,1619,404,151,1034,675,393,774,942,771,1027,1609,59,994,300,618,1457,531,491,1470,1089,536,229,150,46,0,310,1114,924,414,163,333,1797,390,240,10,576,421,452,235,26,1128,137,260,92,1174,757,1040,265,70,658,913,72,21,144,1567,1471,956,375,63,1633,862,664,1039,6,1318,624,169,667,523,449,1130,1314,0,860,0,599,209,14,389,875,414,368,843,424,1094,173,363,575,1243,296,179,431,587,89,80,274,202,752,317,829,1116,8,83,371,556,21,1042,270,521,374,76,0,274,417,147,1415,356,1717,1281,184,667,1169,1185,1881,376,448,92,1252,25,98,1563,535,14,180,327,341,1231,1160,253,1530,1513,152,382,69,528,1417,300,5,966,1412,1329,1527,109,416,172,1073,216,315,357,446,106,279,351,56,1097,303,867,1447,768,766,9,226,761,27,229,35,286,358,205,36,176,233,800,1839,268,321,30,280,105,21,315,1265,0,1172,208,352,120,673,693,449,670,246,15,380,67,168,408,364,1115,1018,601,265,59,52,329,1277,19,4,706,66,776,25,202,356,343,375,27,339,1424,418,184,232,522,83,176,61,646,1150,433,120,33,1380,348,486,669,1049,161,1372,817,13,387,629,808,504,291,119,217,806,177,228,487,872,420,803,19,386,1398,35,1509,955,213,1385,40,184,820,404,1670,723,613,1443,982,263,75,652,57,833,991,296,37,62,1093,1727,8,298,717,206,4,470,170,830,150,350,145,690,180,858,334,20,679,9,37,552,959,71,32,214,344,5,27,1810,628,1622,476,1255,330,552,334,785,212,17,1062,491,41,261,60,241,1479,502,28,1561,435,22,349,12,373,182,463,222,50,709,484,146,400,630,216,78,1239,1512,56,870,1082,1343,901,45,332,602,176,991,439,639,600,215,14,316,202,1396,672,908,580,805,282,1420,493,216,1686,12,240,1216,797,109,308,224,424,1260,453,673,380,13,3,842,389,1421,350,308,634,187,1707,173,1174,597,562,1020,76,199,579,1045,335,465,37,70,784,248,137,35,466,298,108,1553,105,99,27,875,936,107,1129,609,1144,378,89,549,556,184,1016,11,568,953,8,595,0,670,651,684,172,266,505,103,68,37,392,289,259,1295,7,558,493,1118,13,522,378,595,96,1300,978,578,24,316,4,1316,20,39,194,99,236,2,1346,108,68,317,85,822,602,944,535,54,160,455,954,59,257,1511,232,1377,38,191,104,1336,624,1140,512,1384,332,54,61,207,75,1153,93,550,741,801,13,20,8,1144,567,97,675,1000,10,228,197,51,901,77,521,1871,588,639,670,45,85,843,40,1039,1074,531,1312,60,311,875,1429,917,420,1155,411,218,630,64,374,196,955,917,718,689,900,1419,283,296,529,811,841,459,163,514,63,1150,151,97,919,940,291,873,140,63,922,62,741,260,26,491,731,26,862,180,967,892,546,104,397,293,119,48,46,91,46,1369,73,159,347,13,742,6,103,459,152,533,5,314,825,189,164,9,170,610,156,1420,672,218,1412,1201,1862,202,154,1449,9,32,78,146,1277,281,288,493,31,9,425,633,177,1056,248,711,429,51,370,700,40,69,316,600,187,77,289,39,779,1001,218,1574,1178,260,1206,143,228,1094,810,1098,383,335,36,64,1340,32,536,6,117,55,837,34,273,412,417,741,215,1267,1233,1088,1211,64,6,628,610,213,279,212,274,362,290,191,390,512,1030,892,5,615,575,740,111,119,29,45,439,1342,1167,1239,1304,472,380,56,1599,25,810,1011,21,640,210,558,102,7,270,75,31,134,183,360,270,197,316,6,174,145,326,336,761,703,1344,274,577,425,641,812,11,121,6,506,573,1393,0,287,606,76,595,753,495,669,13,143,241,103,567,693,1661,229,75,96,1521,150,12,562,1107,308,63,258,17,671,13,682,69,280,8,253,970,344,1016,655,489,1073,18,8,1713,96,193,709,112,510,109,118,106,546,241,1388,372,596,900,93,448,209,490,171,615,574,620,42,108,197,784,907,614,490,334,10,369,515,190,809,106,1105,398,423,356,576,83,477,41,1017,480,336,18,277,552,1322,575,449,3,571,123,298,356,743,427,26,305,1708,376,82,296,1341,933,372,35,1052,85,9,454};
    // private static int[] POSITIONS = {16,1,2,0,4,2,7,1,2,14};
    
    public DaySeven() {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private long partTwo() {
        Integer min = Arrays.stream(POSITIONS).boxed().min(Comparator.comparing(x -> x)).get();
        Integer max = Arrays.stream(POSITIONS).boxed().max(Comparator.comparing(x -> x)).get();
        long currentSmallestTotal = Long.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            long total = getFuel(i, POSITIONS);
            if (total < currentSmallestTotal) {
                currentSmallestTotal = total;
            }
        }
        return currentSmallestTotal;
    }

    private long getFuel(int val, int[] positions) {
        long total = 0L;
        for (int pos: positions) {
            int n = Math.abs(pos - val);
            int fuel = n * (n + 1) / 2;
            total = total += fuel;
        }
        return total;
    } 

    private long partOne() {
        Set<Integer> distinct = Arrays.stream(POSITIONS).boxed().collect(Collectors.toSet());
        long currentSmallestTotal = Long.MAX_VALUE;
        for (Integer pos: distinct) {
            long total = getConstantFuel(pos, POSITIONS);
            if (total < currentSmallestTotal) {
                currentSmallestTotal = total;
            }
        }
        return currentSmallestTotal;
    }

    private long getConstantFuel(int val, int[] positions) {
        long total = 0L;
        for (int pos: positions) {
            total += Math.abs(pos - val);
        }
        return total;
    }
}