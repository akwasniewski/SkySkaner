#include <iostream>
#include <random>
#include <string>
#include <sstream>
#include<bits/stdc++.h>
#include <chrono>
#include <iostream>
#include <chrono>
#include <ctime>


using namespace std;


const double AVG_EARTH_RADIUS = 6371.0; // Średni promień Ziemi w kilometrach
const double AVERAGE_FLIGHT_SPEED = 900.0; // Przykładowa średnia prędkość samolotu w kilometrach na godzinę

double degreesToRadians(double degrees) {
    return degrees * M_PI / 180.0;
}

double calculateFlightTime(double lat1, double lon1, double lat2, double lon2) {
    lat1 = degreesToRadians(lat1);
    lon1 = degreesToRadians(lon1);
    lat2 = degreesToRadians(lat2);
    lon2 = degreesToRadians(lon2);

    double dLat = lat2 - lat1;
    double dLon = lon2 - lon1;

    double a = sin(dLat / 2) * sin(dLat / 2) +
               cos(lat1) * cos(lat2) *
               sin(dLon / 2) * sin(dLon / 2);

    double c = 2 * atan2(sqrt(a), sqrt(1 - a));

    double distance = AVG_EARTH_RADIUS * c;
    double flightTime = distance / AVERAGE_FLIGHT_SPEED;

    return flightTime;
}

// // Function to execute SQL queries
// void executeQuery(connection& conn, const std::string& query) {
//     pqxx::work txn(conn);
//     txn.exec(query);
//     txn.commit();
// }

int main() {



    

// 01    ICAO Code	String (3-4 chars, A - Z)
// 02	IATA Code	String (3 chars, A - Z)
// 03	Airport Name	String
// 04	City/Town	String
// 05	Country	String
// 06	Latitude Degrees	Integer [0,360]
// 07	Latitude Minutes	Integer [0,60]
// 08	Latitude Seconds	Integer [0,60]
// 09	Latitude Direction	Char (N or S)
// 10	Longitude Degrees	Integer [0,360]
// 11	Longitude Minutes	Integer [0,60]
// 12	Longitude Seconds	Integer [0,60]
// 13	Longitude Direction	Char (E or W)
// 14	Altitude	Integer [-99999,+99999]
// (Altitude in meters from mean sea level)
// 16	Latitude Decimal Degrees	Floating point [-90,90]
// 17	Longitude Decimal Degrees	Floating point [-180,180]

    vector<string> ICAO;
    vector<string> IATA;
    vector<string> AirportName;
    vector<string> City;
    vector<string> Country;
    vector<double> X;
    vector<double> Y;
    vector<string> Modele;
    vector<string> Airlines;
    vector<string> cities(370);
    vector<int> timezone(370);
    vector<int> timzon;
    
    ifstream file3("cities.txt");

    if (file3.is_open()) {
        int y=0;
        
        for(int i=0; i<94; i++){
            string line;
            getline(file3,line);
            int j=0;
            
            
            while(line[j] != ':'){ cities[y]+=line[j]; j++;} 
            j+=5;
            string li;
            while(line[j] != '\n'){li+=line[j];
            j++;}
            
            timezone[y] = stoi(li);
            cout<<timezone[y]<<endl;
            y++;
        }
            

        file3.close(); // Close the file
    } else {
        cout << "Failed to open the file." << endl;
    }

    ifstream file("dane.txt");

    if (file.is_open()) {
        string line;
        for(int i=0; i<9300; i++){
            string icao,iata,airport,city,country,x,y;
            getline(file, line);
            int j=0;
            while(line[j]!=':'){ icao+=line[j]; j++;}
            j++;
            while(line[j]!=':'){iata+=line[j]; j++; }
            j++;
            while(line[j]!=':'){airport+=line[j]; j++;}
            j++;
            while(line[j]!=':'){city+=line[j]; j++;}
            j++;
            while(line[j]!=':'){ country+=line[j]; j++; }
            j++;
            for(int a=0; a<9; a++){
                while(line[j]!=':') j++;
                j++;
            }
            while(line[j]!=':'){ x+=line[j]; j++;}
            j++;
            while(line[j]!=':' && j<line.size()){ y+=line[j]; j++;}
            j++;

            double pomoc = stod(x);

            bool m = false;
            int q2=0;
            for(int q=0; q<370; q++) if(cities[q]==city){m =true; q2=q;} 
            if(pomoc!=0 && iata!="N/A" && m){
            
                
                ICAO.push_back(icao);
                IATA.push_back(iata);
                AirportName.push_back(airport);
                City.push_back(city);
                Country.push_back(country);
                timzon.push_back(timezone[q2]);
            
                X.push_back(pomoc);
                
                pomoc = stod(y);
                Y.push_back(pomoc);
                //cout<<Y.back()<<endl;
            }
            
        }

        file.close();
    } else {
        cout << "Failed to open the file." << endl;
    }

    ifstream file1("modelesamolotow.txt");

    if (file1.is_open()) {
        string line;
        for(int i=0; i<44; i++){
            getline(file1,line);
            Modele.push_back(line);
        }

        file1.close(); // Close the file
    } else {
        cout << "Failed to open the file." << endl;
    }

    ifstream file2("airlines.txt");

    if (file2.is_open()) {
        string line;
        for(int i=0; i<175; i++){
            getline(file2,line);
            line.erase(line.length() -1);
            Airlines.push_back(line);
        }

        file2.close(); // Close the file
    } else {
        cout << "Failed to open the file." << endl;
    }

    vector<vector<int>> airplanemodels;
    //tabela airplane_models
    cout<<"insert into airplane_models values"<<endl;
    for(int i=0; i<44; i++){
        vector<int> aa;
        int capacityEconomy = rand() % 201 + 100;
        int capacityBusiness = rand() % 41 + 10;
        int capacityFirst = rand() % 16 + 5;
        aa.push_back(capacityEconomy);
        aa.push_back(capacityBusiness);
        aa.push_back(capacityFirst);
        airplanemodels.push_back(aa);
        string wyraz;
        for(int j=0; j<Modele[i].size()-1; j++) wyraz+=Modele[i][j];
        if(i<43)cout<<"("<<i+1<<", '"<<wyraz<<"', "<<capacityEconomy<<", "<<capacityBusiness<<", "<<capacityFirst<<"),"<<endl;
        else cout<<"("<<i+1<<", '"<<wyraz<<"', "<<capacityEconomy<<", "<<capacityBusiness<<", "<<capacityFirst<<");"<<endl;
    }
    cout<<endl;

    cout<<"insert into airplanes values"<<endl;
    for(int i=0; i<200; i++){
        int wyraz = rand()%44 +1;
        if(i<199)cout<<"("<<i+1<<", "<<wyraz<<"),"<<endl;
        else cout<<"("<<i+1<<", "<<wyraz<<");"<<endl;
    }
    cout<<endl;


    cout<<"insert into countries values"<<endl;
    int licz = 1;
    map<string, int> wyn;
    vector<string> WCountry;
    //cout<<Country.size();

    for(int i=0; i<Country.size(); i++){
        if(wyn[Country[i]] == 0){
            wyn[Country[i]] = licz;
            licz++;
            WCountry.push_back(Country[i]);
            //if(Country[i][0] = 'G') cout<<Country[i]<<endl;
        }
    }
    for(int i=0; i<WCountry.size(); i++){
        if(i<WCountry.size()-1)cout<<"("<<i+1<<", '"<<WCountry[i]<<"'),"<<endl;
        else cout<<"("<<i+1<<", '"<<WCountry[i]<<"');"<<endl;
    }
    cout<<endl;

    // DOROBIĆ STREFY CZASOWE

    cout<<"insert into cities values"<<endl;
    licz = 1;
    map<string, int> wyn2;
    vector<int> WCities;
    //cout<<Country.size();

    for(int i=0; i<City.size(); i++){
        if(wyn2[City[i]] == 0){
            wyn2[City[i]] = licz;
            licz++;
            WCities.push_back(i);


            //if(Country[i][0] = 'G') cout<<Country[i]<<endl;
        }
    }
    //POINT(51.48378922368449, -0.12488499963308404),
    for(int i=0; i<WCities.size(); i++){
        int timezone2 = timzon[WCities[i]];
        if(i<WCities.size()-1)cout<<"("<<i+1<<", '"<<City[WCities[i]]<<"', "<<wyn[Country[WCities[i]]]<<", POINT("<<X[WCities[i]]<<", "<<Y[WCities[i]]<<"), "<<timezone2<<"),"<<endl;
        else cout<<"("<<i+1<<", '"<<City[WCities[i]]<<"', "<<wyn[Country[WCities[i]]]<<", POINT("<<X[WCities[i]]<<", "<<Y[WCities[i]]<<"), "<<timezone2<<");"<<endl;
    }
    cout<<endl;

    cout<<"insert into users values"<<endl<<
	"(1,'john123',	'pa$$word1'),"<<endl<<
	"(2,'jessica92',	'1234abcd'),"<<endl<<
	"(3,'bob_smith',	'b0b_pass'),"<<endl<<
	"(4,'sarah_carter',	's@r@h123'),"<<endl<<
	"(5,'max_power',	'p0w3r_m@x'),"<<endl<<
	"(6,'emma_watson',	'harryp0tter'),"<<endl<<
	"(7,'kevin_jones',	'kevin_jones_123'),"<<endl<<
	"(8,'amy_lee',	'evanesc3nce'),"<<endl<<
	"(9,'david_bowie',	'ziggystardust'),"<<endl<<
	"(10,'carla_garcia',	'1q2w3e4r'),"<<endl<<
	"(11,'michael_jordan',	'airjordan23'),"<<endl<<
	"(12,'taylor_swift',	'love$tor'),"<<endl<<
	"(13,'bruce_wayne',	'darkknight'),"<<endl<<
	"(14,'lucy_lawless',	'xena123'),"<<endl<<
	"(15,'justin_bieber',	'belieber4ever'),"<<endl<<
	"(16,'jennifer_lopez',	'jlo12#'),"<<endl<<
	"(17,'chris_evans',	'captainamerica'),"<<endl<<
	"(18,'kristen_stewart',	'edwardcullen'),"<<endl<<
	"(19,'ryan_gosling',	'heygirl!'),"<<endl<<
	"(20,'scarlett_johansson',	'blackwidow'),"<<endl<<
	"(21,'leonardo_dicaprio',	'titanic1997'),"<<endl<<
	"(22,'anne_hathaway',	'princessdiaries'),"<<endl<<
	"(23,'zac_efron',	'highschoolmusical'),"<<endl<<
	"(24,'emily_blunt',	'marypoppins'),"<<endl<<
	"(25,'johnny_depp',	'jacksparrow'),"<<endl<<
	"(26,'sandra_bullock',	'misscongeniality'),"<<endl<<
	"(27,'hugh_jackman',	'wolverine'),"<<endl<<
	"(28,'margot_robbie',	'harleyquinn'),"<<endl<<
	"(29,'tom_holland',	'spiderman'),"<<endl<<
	"(30,'angelina_jolie',	'tombraider');"<<endl<<endl;

    cout<<"insert into airports (id_airport, id_city, code, the_name) values"<<endl;
    
    //cout<<Country.size();

    for(int i=0; i<AirportName.size(); i++){
        if(i<AirportName.size()-1)cout<<"("<<i+1<<", "<<wyn2[City[i]]<<", '"<<IATA[i]<<"', '"<<AirportName[i]<<"'),"<<endl;
        else cout<<"("<<i+1<<", "<<wyn2[City[i]]<<", '"<<IATA[i]<<"', '"<<AirportName[i]<<"');"<<endl;
    }

    cout<<endl;
    cout<<"insert into terminals values"<<endl;
    for(int i=0; i<AirportName.size(); i++){
        int ter = rand()%2 +1;
        if(i<AirportName.size()-1)cout<<"("<<i+1<<", "<<i+1<<", "<<ter<<"),"<<endl;
        else cout<<"("<<i+1<<", "<<i+1<<", "<<ter<<");"<<endl;
    }


    cout<<endl<<"insert into airlines values"<<endl;
    for(int i=0; i<Airlines.size(); i++){
        int air = rand()%(AirportName.size()) +1;
        if(i<Airlines.size()-1)cout<<"("<<i+1<<", "<<air<<", '"<<Airlines[i]<<"'),"<<endl;
        else cout<<"("<<i+1<<", "<<air<<", '"<<Airlines[i]<<"');"<<endl;
    }

    cout<<endl<<"insert into airlines_airplanes values"<<endl;
    for(int i=0; i<200; i++){
        int airp = rand()%(Airlines.size()) +1;
        if(i<199)cout<<"("<<i+1<<", "<<airp<<"),"<<endl;
        else cout<<"("<<i+1<<", "<<airp<<");"<<endl;
    }

    cout<<endl<<"insert into connections values"<<endl;
    for(int i=0; i<10000; i++){
        int airpfrom = rand()%(AirportName.size()) +1;
        int airpto = rand()%(AirportName.size()) +1;
        while(airpfrom == airpto) airpto = rand()%(AirportName.size()) +1;
        int godzinao = rand()%24;
        int mino = rand()%60;
        int lot = calculateFlightTime(X[airpfrom-1], Y[airpfrom-1],X[airpto-1], Y[airpto-1]);
        int godzinap = (godzinao+lot+1)%24;
        int minp = rand()%60;
        if(i<9999){cout<<"("<<i+1<<", "<<airpfrom<<", "<<airpto<<", '";
        cout << std::setw(2) << std::setfill('0')<<godzinao<<":"<<std::setw(2) << std::setfill('0')<<mino<<":00', '"<<std::setw(2) << std::setfill('0')<<godzinap<<":"<<std::setw(2) << std::setfill('0')<<minp<<":00'"<<"),"<<endl;}
        else {cout<<"("<<i+1<<", "<<airpfrom<<", "<<airpto<<", '";
        cout << std::setw(2) << std::setfill('0')<<godzinao<<":"<<std::setw(2) << std::setfill('0')<<mino<<":00', '"<<std::setw(2) << std::setfill('0')<<godzinap<<":"<<std::setw(2) << std::setfill('0')<<minp<<":00'"<<");"<<endl;}
    }
    vector<int> jakimodel;
//(1, 3, 10, '14:25:00', '17:10:00'),
    cout<<endl<<"insert into flights values"<<endl;
    for(int i=0; i<100000; i++){
        int con = rand()%10000 +1;
        int ter = rand()%(AirportName.size())+1;
        int plane = rand()%Modele.size() +1;
        jakimodel.push_back(plane);
        int miesiac = rand()%3 +10;
        int dzien = rand()%30 +1;

        if(i<99999)cout<<"("<<i+1<<", "<<ter<<", "<<con<<", "<<plane<<", '2023-"<<miesiac<<"-"<< std::setw(2) << std::setfill('0')<<dzien<<"'),"<<endl;
        else cout<<"("<<i+1<<", "<<ter<<", "<<con<<", "<<plane<<", '2023-"<<miesiac<<"-"<< std::setw(2) << std::setfill('0')<<dzien<<"');"<<endl;
    }
    cout<<endl<<"insert into tickets values"<<endl;
    int idd=0;
    for(int i=0; i<100000; i++){
        idd++;
        int cena = rand()%1000 +50;
        int bleee = rand()%3;

        if(i<99999){
            cout<<"("<<idd<<", "<<i+1<<", "<<3<<", "<<cena<<"),"<<endl;
            idd++;
            cena*=1,2;
            if(bleee==1)cout<<"("<<idd<<", "<<i+1<<", "<<2<<", "<<cena<<"),"<<endl;
            idd++;
            cena*=1,5;
            if(bleee==2)cout<<"("<<idd<<", "<<i+1<<", "<<1<<", "<<cena<<"),"<<endl;
        }
        else cout<<"("<<idd<<", "<<i+1<<", "<<3<<", "<<cena<<");"<<endl;
    }
    
   

    return 0;
}
