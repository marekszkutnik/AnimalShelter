CREATE TABLE jezyki (
    anieglski              CHAR(2 CHAR),
    niemiecki              CHAR(2 CHAR),
    francuski              CHAR(2 CHAR),
    wloski                 CHAR(2 CHAR),
    rosyjski               CHAR(2 CHAR),
    japonski               CHAR(2 CHAR),
    chinski                CHAR(2 CHAR),
    urzednik_id_urzednik   NUMBER(5) NOT NULL
);

ALTER TABLE jezyki
    ADD CHECK ( anieglski IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

ALTER TABLE jezyki
    ADD CHECK ( niemiecki IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

ALTER TABLE jezyki
    ADD CHECK ( francuski IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

ALTER TABLE jezyki
    ADD CHECK ( wloski IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

ALTER TABLE jezyki
    ADD CHECK ( rosyjski IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

ALTER TABLE jezyki
    ADD CHECK ( japonski IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

ALTER TABLE jezyki
    ADD CHECK ( chinski IN (
        'A1',
        'A2',
        'B1',
        'B2',
        'C1',
        'C2'
    ) );

CREATE TABLE klienci (
    id_klient                  NUMBER(5) NOT NULL,
    imie                       VARCHAR2(20 CHAR) NOT NULL,
    nazwisko                   NUMBER(5) NOT NULL,
    data_urodzenia             DATE NOT NULL,
    zdolnosc_adopcyjna         CHAR(1 CHAR) NOT NULL,
    miasto                     VARCHAR2(30 CHAR),
    ulica                      VARCHAR2(30 CHAR),
    numer_ulicy                VARCHAR2(5),
    numer_mieszkania           VARCHAR2(5),
    kod_pocztowy               CHAR(5) NOT NULL,
    numer_kontaktowy           VARCHAR2(12),
    schronisko_id_schronisko   NUMBER(5) NOT NULL
);

ALTER TABLE klienci
    ADD CHECK ( zdolnosc_adopcyjna IN (
        'N',
        'Z'
    ) );

ALTER TABLE klienci ADD CONSTRAINT klient_pk PRIMARY KEY ( id_klient );

CREATE TABLE kliniki_weterynaryjne (
    id_klinika         NUMBER(5) NOT NULL,
    specjalnosc        VARCHAR2(20 CHAR) NOT NULL,
    koszt_umowy        NUMBER(10, 2) NOT NULL,
    zawarcie_umowy     DATE NOT NULL,
    koniec_umowy       DATE NOT NULL,
    miasto             VARCHAR2(30 CHAR) NOT NULL,
    ulica              VARCHAR2(30 CHAR) NOT NULL,
    numer_ulicy        VARCHAR2(5) NOT NULL,
    numer_mieszkania   VARCHAR2(5),
    kod_pocztowy       CHAR(5) NOT NULL,
    numer_kontaktowy   VARCHAR2(12) NOT NULL
);

ALTER TABLE kliniki_weterynaryjne
    ADD CHECK ( specjalnosc IN (
        'KOT',
        'PIES',
        'PTAK'
    ) );

ALTER TABLE kliniki_weterynaryjne ADD CONSTRAINT klinika_weterynaryjna_pk PRIMARY KEY ( id_klinika );

CREATE TABLE kojce (
    id_kojec           NUMBER(5) NOT NULL,
    gatunek            VARCHAR2(20 CHAR) NOT NULL,
    wymiar             NUMBER(5, 2) NOT NULL,
    pojemnosc          NUMBER(2) NOT NULL,
    sektor_id_sektor   NUMBER(5) NOT NULL
);

ALTER TABLE kojce ADD CONSTRAINT kojec_pk PRIMARY KEY ( id_kojec );

CREATE TABLE Konta
(
	id_konto   NUMBER(10) NOT NULL,
	login      VARCHAR2(30 CHAR) NOT NULL,
	haslo      VARCHAR2(30 CHAR) NOT NULL,
	typ_konta  NUMBER(1) NOT NULL
);

ALTER TABLE Konta ADD CONSTRAINT PK_Konto PRIMARY KEY (id_konto);

ALTER TABLE Konta ADD CONSTRAINT UQ_Konta_login UNIQUE (login);

CREATE TABLE pracownicy (
    id_pracownik               NUMBER(5) NOT NULL,
    imie                       VARCHAR2(20 CHAR) NOT NULL,
    nazwisko                   VARCHAR2(30 CHAR) NOT NULL,
    data_urodzenia             DATE NOT NULL,
    plec                       CHAR(1 CHAR) NOT NULL,
    pesel                      CHAR(11),
    numer_konta_bankowego      CHAR(26 BYTE) NOT NULL,
    data_zatrudnienia          DATE NOT NULL,
    data_rozwiazania_umowy     DATE,
    miasto                     VARCHAR2(30 CHAR) NOT NULL,
    ulica                      VARCHAR2(30 CHAR) NOT NULL,
    numer_ulicy                VARCHAR2(5 BYTE) NOT NULL,
    numer_mieszkania           VARCHAR2(5),
    kod_pocztowy               CHAR(5) NOT NULL,
    numer_kontaktowy           VARCHAR2(12) NOT NULL,
    schronisko_id_schronisko   NUMBER(5) NOT NULL,
    pracownik_type             VARCHAR2(8) NOT NULL
);

ALTER TABLE pracownicy
    ADD CHECK ( plec IN (
        'M',
        'K'
    ) );


ALTER TABLE pracownicy
    ADD CONSTRAINT ch_inh_pracownik CHECK ( pracownik_type IN (
        'Robotnik',
        'Urzednik'
    ) );

ALTER TABLE pracownicy ADD CONSTRAINT pracownik_pk PRIMARY KEY ( id_pracownik );

ALTER TABLE pracownicy ADD CONSTRAINT pracownik_pesel_un UNIQUE ( pesel );

CREATE TABLE robotnicy (
    id_pracownik   NUMBER(5) NOT NULL,
    id_robotnik    NUMBER(5) NOT NULL,
    zezwolenie     CHAR(1 CHAR) NOT NULL,
    ubezpiecznie   CHAR(1) NOT NULL
);

ALTER TABLE robotnicy
    ADD CHECK ( zezwolenie IN (
        'N',
        'T',
        'X'
    ) );

ALTER TABLE robotnicy
    ADD CHECK ( ubezpiecznie IN (
        'N',
        'U'
    ) );

ALTER TABLE robotnicy ADD CONSTRAINT robotnik_pk PRIMARY KEY ( id_pracownik );

ALTER TABLE robotnicy ADD CONSTRAINT robotnik_pkv1 UNIQUE ( id_robotnik );

CREATE TABLE schroniska (
    id_schronisko    NUMBER(5) NOT NULL,
    nazwa            VARCHAR2(30 CHAR) NOT NULL,
    data_zalozenia   DATE NOT NULL,
    budzet           NUMBER(9, 2) NOT NULL
);

ALTER TABLE schroniska ADD CONSTRAINT schronisko_pk PRIMARY KEY ( id_schronisko );

ALTER TABLE schroniska ADD CONSTRAINT schronisko_nazwa_un UNIQUE ( nazwa );

CREATE TABLE "Sektor-Pracownik" (
    sektor_id_sektor                     NUMBER(5) NOT NULL,
    pracownik_id_pracownik               NUMBER(5) NOT NULL, 
    prac_schr_id_schronisko              NUMBER NOT NULL
);

ALTER TABLE "Sektor-Pracownik"
    ADD CONSTRAINT "Sektor-Pracownik_PK" PRIMARY KEY ( sektor_id_sektor,
                                                       pracownik_id_pracownik,
                                                       pracownik_schronisko_id_schronisko );

CREATE TABLE sektory (
    id_sektor                  NUMBER(5) NOT NULL,
    data_zalozenia             DATE NOT NULL,
    godzina_otwarcia           DATE NOT NULL,
    godzina_zamkniecia         DATE NOT NULL,
    miasto                     VARCHAR2(30 CHAR) NOT NULL,
    ulica                      VARCHAR2(30 CHAR) NOT NULL,
    numer_domu                 VARCHAR2(5) NOT NULL,
    kod_pocztowy               CHAR(5) NOT NULL,
    numer_kontaktowy           VARCHAR2(12) NOT NULL,
    schronisko_id_schronisko   NUMBER(5) NOT NULL
);

COMMENT ON COLUMN sektory.id_sektor IS
    'klucz g≥ůwny sektora';

COMMENT ON COLUMN sektory.data_zalozenia IS
    'data otwarcia sektora';

COMMENT ON COLUMN sektory.godzina_otwarcia IS
    'godzina otwarcia sektora';

ALTER TABLE sektory ADD CONSTRAINT sektor_pk PRIMARY KEY ( id_sektor );

ALTER TABLE sektory ADD CONSTRAINT sektor_numer_kontaktowy_un UNIQUE ( numer_kontaktowy );

CREATE TABLE urzednicy (
    id_pracownik       NUMBER(5) NOT NULL,
    id_urzednik        NUMBER(5) NOT NULL,
    doswiadczenie      CHAR(1) DEFAULT 'N' NOT NULL,
    lata_doswiadczen   NUMBER(3) NOT NULL,
    jezyk              VARCHAR2(20 CHAR)
);

ALTER TABLE urzednicy ADD CONSTRAINT urzednik_pk PRIMARY KEY ( id_pracownik );

ALTER TABLE urzednicy ADD CONSTRAINT urzednik_pkv1 UNIQUE ( id_urzednik );

CREATE TABLE wolontariusze (
    id_wolontariusz            NUMBER(5) NOT NULL,
    imie                       VARCHAR2(20 CHAR) NOT NULL,
    nazwisko                   VARCHAR2(30 CHAR) NOT NULL,
    data_urodzenia             DATE NOT NULL,
    plec                       CHAR(1 CHAR) NOT NULL,
    doswiadczenie              CHAR(1) DEFAULT 'N' NOT NULL,
    rodzaj_opieki              VARCHAR2(20 CHAR) NOT NULL,
    dyspozycyjnosc             VARCHAR2(300 CHAR) NOT NULL,
    schronisko_id_schronisko   NUMBER(5) NOT NULL
);

ALTER TABLE wolontariusze
    ADD CHECK ( plec IN (
        'M',
        'K'
    ) );

COMMENT ON COLUMN wolontariusze.doswiadczenie IS
    'doúwiadczenie w opiece nad zwierzÍtami';

ALTER TABLE wolontariusze ADD CONSTRAINT wolontariusz_pk PRIMARY KEY ( id_wolontariusz );

CREATE TABLE wynagrodzenia (
    id_wynagrodzenie         NUMBER(5) NOT NULL,
    data_wydania             DATE NOT NULL,
    wielkosc                 NUMBER(10, 2) NOT NULL,
    waluta                   VARCHAR2(3 CHAR) NOT NULL,
    premia                   NUMBER(5),
    pracownik_id_pracownik   NUMBER(5) NOT NULL
);

ALTER TABLE wynagrodzenia ADD CONSTRAINT wynagrodzenie_pk PRIMARY KEY ( id_wynagrodzenie );

CREATE TABLE wyposazenie (
    id_wyposazenie   NUMBER(5) NOT NULL,
    legowisko        NUMBER(2) NOT NULL,
    miska            NUMBER(2) NOT NULL,
    kuweta           NUMBER(2) NOT NULL,
    pozywienie       CHAR(1 CHAR) NOT NULL,
    picie            CHAR(1 CHAR) NOT NULL,
    obroza           NUMBER(2) NOT NULL,
    smycz            NUMBER(2) NOT NULL,
    kojec_id_kojec   NUMBER(5) NOT NULL
);

ALTER TABLE wyposazenie ADD CONSTRAINT wyposazenie_pk PRIMARY KEY ( id_wyposazenie );

CREATE TABLE zwierzeta (
    id_zwierze                         NUMBER(30) NOT NULL,
    nazwa                              VARCHAR2(30) NOT NULL,
    gatunek                            VARCHAR2(30) NOT NULL,
    rasa                               VARCHAR2(30 CHAR) NOT NULL,
    wielkosc                           CHAR(1 CHAR) NOT NULL,
    kolor                              VARCHAR2(20 CHAR) NOT NULL,
    szczepinie                         CHAR(1 CHAR) NOT NULL,
    data_narodzin                      DATE NOT NULL,
    wolontariusz_id_wolontariusz       NUMBER(5),
    kojec_id_kojec                     NUMBER(5) NOT NULL,
    klient_id_klient                   NUMBER(5), 
    kl_wet_id_klinika                  NUMBER(5)
);

ALTER TABLE zwierzeta ADD CONSTRAINT zwierze_pk PRIMARY KEY ( id_zwierze );

ALTER TABLE jezyki
    ADD CONSTRAINT jezyki_urzednik_fk FOREIGN KEY ( urzednik_id_urzednik )
        REFERENCES urzednicy ( id_urzednik )
            ON DELETE CASCADE;

ALTER TABLE klienci
    ADD CONSTRAINT klient_schronisko_fk FOREIGN KEY ( schronisko_id_schronisko )
        REFERENCES schroniska ( id_schronisko )
            ON DELETE CASCADE;

ALTER TABLE kojce
    ADD CONSTRAINT kojec_sektor_fk FOREIGN KEY ( sektor_id_sektor )
        REFERENCES sektory ( id_sektor )
            ON DELETE CASCADE;

ALTER TABLE pracownicy
    ADD CONSTRAINT pracownik_schronisko_fk FOREIGN KEY ( schronisko_id_schronisko )
        REFERENCES schroniska ( id_schronisko )
            ON DELETE CASCADE;

ALTER TABLE robotnicy
    ADD CONSTRAINT robotnik_pracownik_fk FOREIGN KEY ( id_pracownik )
        REFERENCES pracownicy ( id_pracownik );

ALTER TABLE sektory
    ADD CONSTRAINT sektor_schronisko_fk FOREIGN KEY ( schronisko_id_schronisko )
        REFERENCES schroniska ( id_schronisko )
            ON DELETE CASCADE;

ALTER TABLE "Sektor-Pracownik"
    ADD CONSTRAINT "Sektor-Pracownik_Pracownik_FK" FOREIGN KEY ( pracownik_id_pracownik )
        REFERENCES pracownicy ( id_pracownik )
            ON DELETE CASCADE;

ALTER TABLE "Sektor-Pracownik"
    ADD CONSTRAINT "Sektor-Pracownik_Sektor_FK" FOREIGN KEY ( sektor_id_sektor )
        REFERENCES sektory ( id_sektor )
            ON DELETE CASCADE;

ALTER TABLE urzednicy
    ADD CONSTRAINT urzednik_pracownik_fk FOREIGN KEY ( id_pracownik )
        REFERENCES pracownicy ( id_pracownik );

ALTER TABLE wolontariusze
    ADD CONSTRAINT wolontariusz_schronisko_fk FOREIGN KEY ( schronisko_id_schronisko )
        REFERENCES schroniska ( id_schronisko )
            ON DELETE CASCADE;

ALTER TABLE wynagrodzenia
    ADD CONSTRAINT wynagrodzenie_pracownik_fk FOREIGN KEY ( pracownik_id_pracownik )
        REFERENCES pracownicy ( id_pracownik )
            ON DELETE CASCADE;

ALTER TABLE wyposazenie
    ADD CONSTRAINT wyposazenie_kojec_fk FOREIGN KEY ( kojec_id_kojec )
        REFERENCES kojce ( id_kojec )
            ON DELETE CASCADE;

ALTER TABLE zwierzeta
    ADD CONSTRAINT zwierze_klient_fk FOREIGN KEY ( klient_id_klient )
        REFERENCES klienci ( id_klient )
            ON DELETE SET NULL;

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE zwierzeta
    ADD CONSTRAINT zw_kl_wet_fk FOREIGN KEY ( kl_wet_id_klinika )
        REFERENCES kliniki_weterynaryjne ( id_klinika )
            ON DELETE SET NULL;

ALTER TABLE zwierzeta
    ADD CONSTRAINT zwierze_kojec_fk FOREIGN KEY ( kojec_id_kojec )
        REFERENCES kojce ( id_kojec )
            ON DELETE CASCADE;

ALTER TABLE zwierzeta
    ADD CONSTRAINT zwierze_wolontariusz_fk FOREIGN KEY ( wolontariusz_id_wolontariusz )
        REFERENCES wolontariusze ( id_wolontariusz )
            ON DELETE SET NULL;

CREATE OR REPLACE TRIGGER fknto_zwierzeta BEFORE
    UPDATE OF klient_id_klient ON zwierzeta
    FOR EACH ROW
BEGIN
    IF :old.klient_id_klient IS NOT NULL THEN
        raise_application_error(-20225, 'Non Transferable FK constraint Zwierze_Klient_FK on table Zwierzeta is violated');
    END IF;
END;
/

CREATE OR REPLACE TRIGGER arc_fkarc_1_urzednicy BEFORE
    INSERT OR UPDATE OF id_pracownik ON urzednicy
    FOR EACH ROW
DECLARE
    d VARCHAR2(8);
BEGIN
    SELECT
        a.pracownik_type
    INTO d
    FROM
        pracownicy a
    WHERE
        a.id_pracownik = :new.id_pracownik;

    IF ( d IS NULL OR d <> 'Urzednik' ) THEN
        raise_application_error(-20223, 'FK Urzednik_Pracownik_FK in Table Urzednicy violates Arc constraint on Table Pracownicy - discriminator column Pracownik_TYPE doesn''t have value ''Urzednik'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

CREATE OR REPLACE TRIGGER arc_fkarc_1_robotnicy BEFORE
    INSERT OR UPDATE OF id_pracownik ON robotnicy
    FOR EACH ROW
DECLARE
    d VARCHAR2(8);
BEGIN
    SELECT
        a.pracownik_type
    INTO d
    FROM
        pracownicy a
    WHERE
        a.id_pracownik = :new.id_pracownik;

    IF ( d IS NULL OR d <> 'Robotnik' ) THEN
        raise_application_error(-20223, 'FK Robotnik_Pracownik_FK in Table Robotnicy violates Arc constraint on Table Pracownicy - discriminator column Pracownik_TYPE doesn''t have value ''Robotnik'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

CREATE SEQUENCE pracownik_id_seq
START WITH 1 
MAXVALUE 999999999999999999999999999 
MINVALUE 1 
NOCYCLE CACHE 20 
NOORDER;

CREATE OR REPLACE TRIGGER pracownicy_trg1
BEFORE INSERT ON pracownicy 
REFERENCING NEW AS New OLD AS Old FOR EACH ROW BEGIN 
:new.id_pracownik := id_pracownik_seq.nextval; 
END pracownicy_trg1; 

ALTER TABLE pracownicy ADD id_pracownik NUMBER DEFAULT pracownik_id_seq.nextval;

CREATE INDEX indeks_kojec ON Zwierzeta (Kojec_ID_Kojec);
CREATE INDEX indeks_gatunek ON Zwierzeta (Gatunek);
CREATE INDEX indeks_lokalizacja_kojca ON kojce (Sektor_id_Sektor);
CREATE INDEX indeks_pracownika ON pracownicy (Miasto);
CREATE INDEX indeks_umowa ON Kliniki_Weterynaryjne (Koniec_umowy);