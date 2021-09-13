import java.util.Scanner;
import java.util.Map;
class RouteSum{
    String route;
    int sumOfThisRoute;
    public RouteSum(){
        route = "";
        sumOfThisRoute = 0;
    }
    public RouteSum(String firstPoint){
        this.AddToRoute(firstPoint);
    }
    public String getRoute(){
        return route;
    }
    public int getSumOfThisRoute(){
        return sumOfThisRoute;
    }
    public void setRoute(String route){
        this.route = route;
    }
    public void setSumOfThisRoute(int sumOfThisRoute){
        this.sumOfThisRoute = sumOfThisRoute;
    }

    public void AddToRoute(String addingRoute){
        if(route == null){
            this.route = addingRoute+"-->";
        }
        else
            this.route+=addingRoute+"-->";
    }
    public void AddToSum(int addingCost){
        this.sumOfThisRoute+=addingCost;
    }
    public void CopyRouteSum(RouteSum obj){
        this.route = obj.route;
        this.sumOfThisRoute = obj.sumOfThisRoute;
    }
}

public class City {

    //Helper Class
    private class CityAndCost{
        private int index;
        private int cost;
        CityAndCost(int index , int cost){
            this.index = index;
            this.cost = cost;
        }
        public void setIndex(int index){
            if(index != 0)
                this.index = index;
        }
        public void setCost(int cost){
            if(cost != 0)
                this.cost = cost;
        }
        public int getIndex(){
            return index;
        }
        public int getCost(){
            return cost;
        }
    }

    //Fields
    private String name;
    private int numberOfNeighbors;
    private int cityIndex;
    private CityAndCost cityAndCost[];

    //Constructor
    public City(String name, int numberOfNeighbors,int cityIndex){
        this.name = name;
        this.cityIndex = cityIndex;
        this.numberOfNeighbors = numberOfNeighbors;
        this.cityAndCost = new CityAndCost[numberOfNeighbors];
        for(int i = 0; i < numberOfNeighbors; i++){
            int index,cost;
            Scanner sc = new Scanner(System.in);
            index = sc.nextInt();
            cost = sc.nextInt();
            cityAndCost[i] = new CityAndCost(index,cost);
        }
    }
    public City(){
        this.name = "Not Name";
        this.cityIndex = 0;
        this.numberOfNeighbors = 0;
        this.cityAndCost = null;
    }


    // Getters and Setters of name of City, number of neighbors ,
    // (for amount of route and their cost correspond Array cityAndCost), and city Index
    public String getName(){
        return name;
    }
    public int getNumberOfNeighbors(){
        return numberOfNeighbors;
    }
    public CityAndCost getCityAndCostByIndex(int index){
        return this.cityAndCost[index];
    }
    public int getCityIndex(){
        return cityIndex;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setNumberOfNeighbors(int numberOfNeighbors){
         this.numberOfNeighbors = numberOfNeighbors;
    }
    public void setCityAndCostByIndex(int index,CityAndCost cityAndCost){
       this.cityAndCost[index] = cityAndCost;
    }
    public void setCityIndex(int cityIndex){
        this.cityIndex = cityIndex;
    }

    public int getCityInMass (int index,City mass[]){
        for(int i = 0; i < mass.length; i++){
            if(index == mass[i].cityIndex){
                return i;
            }
        }
        return 0;
    }
    public String findCityByIndex (int index,City mass[]){
        for(int i = 0; i < mass.length; i++){
            if(index == mass[i].cityIndex){
                return mass[i].getName();
            }
        }
        return "Not Find";
    }
    public String getPermitedNeighbors(int number,RouteSum find){
        int start = 0,end = 0, k =0;
        end = find.route.indexOf("-->",start);
        while(start != -1){
            if(number == k){
                return find.route.substring(start,end);
            }
            if(start == -1){
                continue;
            }else{
                start = end + 3;
                end = find.route.indexOf("-->",start);

            }
            k++;
        }
        return "Not Found";
    }

    private RouteSum Split (City city[], String startPoint,int index, String EndPoint,RouteSum find) {
        RouteSum[] finalRoute, tmp;
        int start = 0, end = find.route.indexOf("-->",start),permition = 0, index_final = 0,index_tmp = 0;
        while(end != -1){
            start = end + 3;
            end = find.route.indexOf("-->",start); /// count number of city in route
            permition++;
        }
        int num = city[index].numberOfNeighbors - (permition-1);
        if(num <= 0)
            num = permition - city[index].numberOfNeighbors;

        finalRoute = new RouteSum[num];
        tmp = new RouteSum[num];
        for(int i = 0; i < num; i++){
            finalRoute[i] = new RouteSum();tmp[i] = new RouteSum();
            finalRoute[i].CopyRouteSum(find);tmp[i].CopyRouteSum(find);
        }
        Boolean isEndAll = true;
        do{
            int k = 0;
            for(int neigbor = 0; neigbor < city[index].numberOfNeighbors;){
                    String findInRoute = getPermitedNeighbors(k,find), /// get name of the city from route
                    neigbor_dash = findCityByIndex(city[index].cityAndCost[neigbor].index,city);//get name of neighbor of this city
                    if(neigbor_dash.compareTo(findInRoute) == 0){///if both city equal, then go to the next one
                        k++;neigbor++;
                    }
                    else if(neigbor_dash.compareTo(findInRoute) != 0){/// or if city not equal
                        while(k != permition){
                            findInRoute = getPermitedNeighbors(k,find);
                            if((neigbor_dash.compareTo(findInRoute)!= 0))//and check
                                k++;
                            else
                                break;
                        }
                        if(k == permition){// that all of this not equal this city(hence his not in route)
                            tmp[index_tmp].AddToRoute(findCityByIndex(city[index].cityAndCost[neigbor].index,city));//adding
                            tmp[index_tmp].AddToSum(city[index].cityAndCost[neigbor].cost);// adding
                            String stringCompare = tmp[index_tmp].route.substring((tmp[index_tmp].route.length() - (3+EndPoint.length())),(tmp[index_tmp].route.length() - 3));///get last city from the route
                            if(stringCompare.compareTo(EndPoint) == 0){/// check if this not end point
                                finalRoute[index_final].CopyRouteSum(tmp[index_tmp]);
                                index_final++;//if like that copy to final mass and go to next index

                            }
                            else{
                                int newIndex = getCityInMass(city[index].cityAndCost[neigbor].index,city);//else getting index of this city in mass
                                tmp[index_tmp] = Split(city,startPoint,newIndex,EndPoint,tmp[index_tmp]);// and again are split by neighbors
                            }
                                index_tmp++;
                            neigbor++;
                        }

                    }
                    if(k == permition)
                    k = 0;
            }
            int p = 0;
            while(p != num){
                String stringCompare = tmp[p].route.substring((tmp[p].route.length() - (3+EndPoint.length())),(tmp[p].route.length() - 3));
                isEndAll &= (stringCompare.compareTo(EndPoint) == 0);
                p++;
            }

        }while(!isEndAll); ///while all route not rich and point

        if(num == 1){
            find = tmp[0];
        }
        else{
            for (int i = 0; i < tmp.length - 1; i++){
                for (int j = 0; j < (tmp.length - i-1); j++){
                    if(tmp[j+1].sumOfThisRoute < tmp[j].sumOfThisRoute){
                            RouteSum tpn = new RouteSum();
                            tpn.CopyRouteSum(tmp[j+1]);
                            tmp[j+1].CopyRouteSum(tmp[j]);
                            tmp[j].CopyRouteSum(tpn);
                    }
                }
            }
        }
        find = tmp[0];
        return find;
    }

    public int pathFinder(int amt_Path_nd_fd, String start_End[], City cityMass[]){
        for(int i = 0; i < amt_Path_nd_fd; i++){
            String startPoint,endPoint;
            int tmp = start_End[i].indexOf(" ");
            startPoint = start_End[i].substring(0,tmp); // Get start point
            endPoint = start_End[i].substring((1+tmp),start_End[i].length());// Get end point
            RouteSum[]arrayofSum = new RouteSum[0];
            for(int j = 0; j < cityMass.length;j++){
                if(cityMass[j].getName().equalsIgnoreCase((startPoint))){
                    arrayofSum =  new RouteSum[cityMass[j].numberOfNeighbors];              //
                    for(int intial = 0; intial < cityMass[j].numberOfNeighbors; intial++){ //Initialize mass of a start point
                        arrayofSum[intial] = new RouteSum(startPoint);                     //
                    }                                                                      //
                    for(int neighbor = 0; neighbor < cityMass[j].numberOfNeighbors; neighbor++){
                        if(!endPoint.equalsIgnoreCase(findCityByIndex(cityMass[j].cityAndCost[neighbor].index,cityMass))){      /// if neighbor not endPoint
                            arrayofSum[neighbor].AddToRoute(findCityByIndex(cityMass[j].cityAndCost[neighbor].index,cityMass)); /// add neighbor to route
                            arrayofSum[neighbor].AddToSum(cityMass[j].cityAndCost[neighbor].cost);                              /// add cost of route to him
                            int index =  getCityInMass(cityMass[j].cityAndCost[neighbor].index,cityMass);                       /// get index neighbor in mass
                            arrayofSum[neighbor] = Split(cityMass,startPoint,index,endPoint,arrayofSum[neighbor]);              /// end start split
                        }
                        else{
                            arrayofSum[neighbor].AddToRoute(findCityByIndex(cityMass[j].cityAndCost[neighbor].index,cityMass));//or just add route and sum
                            arrayofSum[neighbor].AddToSum(cityMass[j].cityAndCost[neighbor].cost);                             //add go to the next neighbor
                        }

                    }
                }
            }
            for (int k = 0; k < arrayofSum.length; k++){
                for (int j = 0; j < (arrayofSum.length-1); j++){
                    if(arrayofSum[j+1].sumOfThisRoute < arrayofSum[j].sumOfThisRoute){
                        RouteSum tpn = new RouteSum();
                        tpn.CopyRouteSum(arrayofSum[j+1]);                  //For sorting array of find route, and receiving first element of array with smallest sumOfRoute
                        arrayofSum[j+1].CopyRouteSum(arrayofSum[j]);
                        arrayofSum[j].CopyRouteSum(tpn);
                    }
                }
            }
            System.out.println(String.format("Route : %s, Sum : %d is smallest",arrayofSum[0].getRoute(),
                    arrayofSum[0].getSumOfThisRoute()));

        }
        return 0;
    }

    public City createCity(){///Create city with index and neighbors(which include his index and cost to travel to them)
        City obj = new City();
        Scanner sc = new Scanner(System.in);
        System.out.println("Set Name");
        obj.setName(sc.nextLine());
        System.out.println("Set Index of city");
        obj.setCityIndex(sc.nextInt());
        System.out.println("Set Number of neighbors of city");
        obj.setNumberOfNeighbors(sc.nextInt());
        obj.cityAndCost = new CityAndCost[obj.numberOfNeighbors];
        System.out.println(String.format("Set create routh to %d neighbors",obj.numberOfNeighbors));
        System.out.println("With his index and cost");
        for(int i = 0; i < obj.numberOfNeighbors; i++){
            int index,cost;
            index = sc.nextInt();
            cost = sc.nextInt();
            obj.cityAndCost[i] = new CityAndCost(index,cost);
        }
        return  obj;
    }

}
