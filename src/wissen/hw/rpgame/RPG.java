package wissen.hw.rpgame;

import java.util.Scanner;

public class RPG {

    static final String KUTU = "\u25A1";
    static final String FIGHT = "\u266B";
    static final String GULENYUZ = "\u263A";
    static final String UZGUNYUZ = "\u263B";
    static Scanner oku = new Scanner(System.in);
    static Player P = new Player(),
            M1 = new Player(),
            M2 = new Player(),
            M3 = new Player(),
            M4 = new Player();

    String[][] map = new String[11][11];
    int[][] randomSira = new int[4][2];

    // isMx'ler Kutudaki Monsterin Hangi Monster Olduğunu Belirtmek İçin
    // oyuncuDurum, Oyuncunun Ölüp Ölmediği Bilgisini Verir
    // isFight, Savaşmaya Devam Edilsin mi Sorusunu Cevaplar
    // isFull randomSira Arrayinde Belirlenen Koordnatın Dolu Olup Olmadığını Verir
    boolean isFull, isM1 = true, isM2 = true, isM3 = true, isM4 = true,
            isP = true, isFight = false, isContinue = false, oyuncuDurum = true;

    int sira = 1, kisiSayi = 2;
    int Pr = P.RC.col = 5, Pc = P.RC.row = 5, oldPr, oldPc;
    int M1r, M1c, M2r, M2c, M3r, M3c, M4r, M4c;
    String enemy = "";

    // Constructor: İçinde Program İlk Çalıştığında Standart Harita Oluşturulur.
    // *** Tüm 4 Monster İçin Koordinatlarını Tutan randomSira Arrayinin İçindeki 
    // Tüm Elemanlarını 11'den Yukarı Tuttum. *** WARNING!
    public RPG() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == Pr && j == Pc) {
                    map[i][j] = P.playerName;
                } else {
                    map[i][j] = KUTU;
                }
            }
        }
        for (int i = 0; i < randomSira.length; i++) {
            for (int j = 0; j < randomSira[0].length; j++) {
                randomSira[i][j] = 12;
            }
        }
        getCreateCoordinates();
    }

    // Monster İçin Bir Random Koordinat Verir...
    public int getGiveCoordinate() {
        return (int) (Math.random() * 11);
    }

    // Tum Monsterler İçin Random Koordinatlar Belirlenir...
    private void getCreateCoordinates() {
        // Her Oyuncu İçin Ayrı Ayrı Koordinatlar Üretilir
        while (true) {
            M1r = getGiveCoordinate();
            M1c = getGiveCoordinate();
            isIndexEmpty(M1r, M1c);
            if (!isFull) {
                randomSira[0][0] = M1r;
                randomSira[0][1] = M1c;
                break;
            }
        }
        while (true) {
            M2r = getGiveCoordinate();
            M2c = getGiveCoordinate();
            isIndexEmpty(M2r, M2c);
            if (!isFull) {
                randomSira[1][0] = M2r;
                randomSira[1][1] = M2c;
                break;
            }
        }
        while (true) {
            M3r = getGiveCoordinate();
            M3c = getGiveCoordinate();
            isIndexEmpty(M3r, M3c);
            if (!isFull) {
                randomSira[2][0] = M3r;
                randomSira[2][1] = M3c;
                break;
            }
        }
        while (true) {
            M4r = getGiveCoordinate();
            M4c = getGiveCoordinate();
            isIndexEmpty(M4r, M4c);
            if (!isFull) {
                randomSira[3][0] = M4r;
                randomSira[3][1] = M4c;
                break;
            }
        }
    }

    // Oyuncuların Tüm Özellikleri Üretilir...
    public void setFillPlayer() {
        P.playerName = "P";
        P.hP = 100;
        P.damage = 40;
        P.isMonster = false;
        M1.playerName = "1";
        M1.hP = getRandomHP();
        M1.damage = 60;
        M1.isMonster = true;
        M2.playerName = "2";
        M2.hP = getRandomHP();
        M2.damage = 60;
        M2.isMonster = true;
        M3.playerName = "3";
        M3.hP = getRandomHP();
        M3.damage = 60;
        M3.isMonster = true;
        M4.playerName = "4";
        M4.hP = getRandomHP();
        M4.damage = 60;
        M4.isMonster = true;
    }

    // Her Monster İçin Kullanılmak Üzere Random HP Üretilir...
    public int getRandomHP() {
        return 20 + (int) (Math.random() * (70 - 20));
    }

    // Random Gelen Koordinatta Monster Yada Oyuncu Olup Olmadığına Bakılır...
    public boolean isIndexEmpty(int row, int col) {
        // eklendiMi, Daha Önce randomSira Arrayine Eklenip Eklenmediği Bilgisini Verir

        boolean eklendiMi = false;
        for (int i = 0; i < randomSira.length; i++) {
            for (int j = 0; j < 1; j++) {
                if (randomSira[i][0] == row && randomSira[i][1] == col) {
                    eklendiMi = true;
                    isFull = true;
                    break;
                }
            }
        }
        // Eğer Eklenmemişse, Ve Yerde Kutu Varsa (Yani Boşsa), isFull değildir Bilgisi verir, 
        // Kutu Yoksa isFull'dur Verir
        if (!eklendiMi) {
            if (map[row][col].equals("\u25A1")) {
                isFull = false;
            } else {
                isFull = true;
            }
        }
        return isFull;
    }

    // Oyuncu Adları Haritaya Yazılır...
    // Oyuncu İçin Belirlenen Koordinatları, Oyuncunun Sembolunu ve Oyuncunun Hangi Oyuncu Olduğu Bilgisini Boolean Alır,
    public void setIntoMap(int row, int col, String playerSymbol, boolean isPlayer) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == row && j == col && isPlayer) {
                    map[i][j] = playerSymbol;
                } else if (!map[i][j].equals(KUTU)) {
                    continue;
                } else {
                    map[i][j] = KUTU;
                }
            }
        }
    }

    // Harita Oluşturulur ve
    // Harita Ekrana Yazılır...
    public void getGraphMap() {
        setIntoMap(M1r, M1c, M1.playerName, isM1);
        setIntoMap(M2r, M2c, M2.playerName, isM2);
        setIntoMap(M3r, M3c, M3.playerName, isM3);
        setIntoMap(M4r, M4c, M4.playerName, isM4);
        setIntoMap(Pr, Pc, P.playerName, isP);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Oyuncu İçin Yeni Hareket Hamlesi Oluşturulur...
    public void setNewMovement() {
        String yon = "";
        while (true) {
            while (true) {
                System.out.print("Enter Move (wasd) : ");
                yon = oku.next();
                if (yon.equals("w") || yon.equals("s") || yon.equals("a") || yon.equals("d")) {
                    break;
                } else {
                    System.out.println("\t\t\tLütfen Sadece \"wasd\" Girin");
                }
            }

            oldPr = Pr;
            oldPc = Pc;
            if (yon.equals("w")) {
                Pr = Pr - 1;
                if (P.hP != 100) {
                    P.hP += 10;
                }
            } else if (yon.equals("s")) {
                Pr = Pr + 1;
                if (P.hP != 100) {
                    P.hP += 10;
                }
            } else if (yon.equals("a")) {
                Pc = Pc - 1;
                if (P.hP != 100) {
                    P.hP += 10;
                }
            } else if (yon.equals("d")) {
                Pc = Pc + 1;
                if (P.hP != 100) {
                    P.hP += 10;
                }
            }
            if (!(Pr >= 0 && Pr < 11) || !(Pc >= 0 && Pc < 11)) {
                System.out.println("Harita Dışına Çıktınız. Yeniden Deneyiniz.");
                Pr = oldPr;
                Pc = oldPc;
            } else {
                break;
            }
        }
    }

    // Oyuncunun Yeni Koordinatına Göre Harita Güncellenir...
    public void getUpdateMap() {
        // eski yere kutu konur
        map[oldPr][oldPc] = KUTU;
        if (!map[Pr][Pc].equals(KUTU)) {
            if (map[Pr][Pc].equals("1") || map[Pr][Pc].equals("2")
                    || map[Pr][Pc].equals("3") || map[Pr][Pc].equals("4")) {
                isFight = true;
                if (map[Pr][Pc].equals("1")) {
                    isM1 = false;
                    enemy = M1.playerName;
                } else if (map[Pr][Pc].equals("2")) {
                    isM2 = false;
                    enemy = M2.playerName;
                } else if (map[Pr][Pc].equals("3")) {
                    isM3 = false;
                    enemy = M3.playerName;
                } else if (map[Pr][Pc].equals("4")) {
                    isM4 = false;
                    enemy = M4.playerName;
                }
            } else if (map[oldPr][oldPc].equals(FIGHT)) {
                isFight = false;
            }
            if (isFight) {
                isP = false;
                map[Pr][Pc] = FIGHT;
            }
        } else if (map[Pr][Pc].equals(KUTU)) {
            isFight = false;
            map[Pr][Pc] = P.playerName;
        }
        System.out.println("PlayerHp: " + P.hP + "HP");
        getGraphMap();
    }

    // Sira Değiştirir
    public int turnOther(int sira) {
        return (sira + 1) % kisiSayi;
    }

    // Atak Yapıldıktan Sonra Canavar Kaldı Mı Bilgisi Verir, Varsa Oyun Devam Eder
    public boolean canavarVarMi() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].equals(M1.playerName) || map[i][j].equals(M2.playerName)
                        || map[i][j].equals(M3.playerName) || map[i][j].equals(M4.playerName)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Her Saldırıdan Sonra Oyuncu Yaşıyor Mu Bilgisi Verir, Yaşıyorsa Oyun Devam Eder
    public boolean oyuncuYasiyorMu() {
        if (P.hP <= 0) {
            return false;
        } else {
            return true;
        }
    }

    // Atak Yaptırır
    public void doAttack() {
        Player enemy = new Player();
        enemy.playerName = this.enemy;
        int cnt = 0;
        if (enemy.playerName.equals(M1.playerName)) {
            enemy = M1;
        } else if (enemy.playerName.equals(M2.playerName)) {
            enemy = M2;
        } else if (enemy.playerName.equals(M3.playerName)) {
            enemy = M3;
        } else if (enemy.playerName.equals(M4.playerName)) {
            enemy = M4;
        }
        System.out.println("First EnemyHp : " + enemy.hP + "HP");
        System.out.println("First PlayerHp : " + P.hP + "HP");
        while (true) {
            int next = turnOther(sira);
            System.out.println("---Attack---");
            if (next == 0) {
                System.out.println("Player do attack..");
                enemy.hP -= P.damage;
                System.out.println("EnemyHp : " + enemy.hP + "HP");
                System.out.println("PlayerHp : " + P.hP + "HP");
                sira++;
            } else if (next == 1) {
                System.out.println("Monster do attack..");
                P.hP -= enemy.damage;
                System.out.println("EnemyHp : " + enemy.hP + "HP");
                System.out.println("PlayerHp : " + P.hP + "HP");
                sira++;
            }
            if (P.hP <= 0 || enemy.hP <= 0) {
                sira = 1;
                break;
            }
        }
        if (P.hP <= 0) {
            isFight = false;
            System.out.println("\t\tPlayer losed " + UZGUNYUZ);
            oyuncuDurum = false;
        } else if (enemy.hP <= 0) {
            System.out.println("\tEnemy was destroyed !\n");
            if (P.hP == 100) {
                System.out.println("Remain Player Healty : " + P.hP + "HP\n");
            } else {
                System.out.println("Remain Player Healty : " + P.hP + "HP\n");
            }
            isContinue = canavarVarMi();
            if (isContinue) {
                isFight = true;
            }
        }
    }

    // Savaş Başlatır
    public void getFight() {
        if (isFight) {
            doAttack();
        }
    }

    public static void main(String[] args) {

        RPG game = new RPG();
        game.setFillPlayer();
        game.getGraphMap();
        do {
            game.setNewMovement();
            game.getUpdateMap();
            game.getFight();
        } while (game.canavarVarMi() && game.oyuncuDurum);
        System.out.println("Oyun Bitti !");
        if (!game.canavarVarMi()) {
            System.out.println("\t\tTebrikler Oyunu Siz Kazandınız " + GULENYUZ);
        }

    }

}
