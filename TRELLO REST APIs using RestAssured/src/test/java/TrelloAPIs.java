import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class TrelloAPIs {
    static Variables var=new Variables();

    public static void main(String[] args) {
        //1-Create a new organization
        RestAssured.baseURI="https://api.trello.com/1/organizations";
        RequestSpecification request1=RestAssured.given();
        request1.header("Content-Type","application/json");
        request1.queryParam("displayName","Trello_Org_Testing");
        request1.queryParam("key",var.key);
        request1.queryParam("token",var.token);
        Response response1= request1.post();
        response1.prettyPrint();
        Assert.assertEquals(response1.getStatusCode(), 200);
        JsonPath path1=response1.jsonPath();
        String orgID= path1.getString("id");
        System.out.println("Organization ID is: "+orgID);
        System.out.println("-----------------------------------------------------------------------------------------");

        //2-Get Organizations for a member
        RestAssured.baseURI="https://api.trello.com/1/members/634c1d659f0d4c05aa471ec1/organizations";
        RequestSpecification request2=RestAssured.given();
        request2.queryParam("key",var.key);
        request2.queryParam("token",var.token);
        request2.queryParam("Org_id",orgID);
        Response response2=request2.get();
        response2.prettyPrint();
        Assert.assertEquals(response2.getStatusCode(), 200);
        JsonPath path2=response1.jsonPath();
        String actual1=path2.getString("id");
        Assert.assertEquals(actual1, orgID);
        System.out.println("-----------------------------------------------------------------------------------------");

        //3-Create a board inside an organization
        RestAssured.baseURI="https://api.trello.com/1/boards/";
        RequestSpecification request3=RestAssured.given();
        request3.header("Content-Type","application/json");
        request3.queryParam("name","Trello_Board_Testing");
        request3.queryParam("key",var.key);
        request3.queryParam("token",var.token);
        request3.queryParam("Org_id",orgID);
        Response response3=request3.post();
        response3.prettyPrint();
        Assert.assertEquals(response3.getStatusCode(),200);
        JsonPath path3=response3.jsonPath();
        String boardID= path3.getString("id");
        System.out.println("Board ID is: "+boardID);
        System.out.println("-----------------------------------------------------------------------------------------");

        //4-Get board of an organization: "https://api.trello.com/1/organizations/{id}/boards?key=APIKey&token=APIToken"
        RestAssured.baseURI="https://api.trello.com";
        RequestSpecification request4=RestAssured.given();
        request4.basePath("/1/organizations/"+orgID+"/boards");
        request4.queryParam("key",var.key);
        request4.queryParam("token",var.token);
        Response response4=request4.get();
        response4.prettyPrint();
        Assert.assertEquals(response4.getStatusCode(), 200);
        JsonPath path4=response4.jsonPath();
        String actual2=path4.getString("id");
        Assert.assertEquals(actual2,"["+boardID+"]");
        System.out.println("-----------------------------------------------------------------------------------------");

        //5- Create a new list: "https://api.trello.com/1/lists?name={name}&idBoard=5abbe4b7ddc1b351ef961414&key=APIKey&token=APIToken"
        RestAssured.baseURI="https://api.trello.com/1/lists";
        RequestSpecification request5=RestAssured.given();
        request5.header("Content-Type","application/json");
        request5.queryParam("name","Trello_List_Testing");
        request5.queryParam("idBoard",boardID);
        request5.queryParam("key",var.key);
        request5.queryParam("token",var.token);
        Response response5=request5.post();
        response5.prettyPrint();
        Assert.assertEquals(response5.getStatusCode(),200);
        JsonPath path5=response5.jsonPath();
        String listID= path5.getString("id");
        System.out.println("List ID is: "+listID);
        System.out.println("-----------------------------------------------------------------------------------------");

        //6-Get all lists on a board: "https://api.trello.com/1/boards/{id}/lists?key=APIKey&token=APIToken"
        RestAssured.baseURI="https://api.trello.com";
        RequestSpecification request6=RestAssured.given();
        request6.basePath("/1/boards/"+boardID+"/lists");
        request6.queryParam("key",var.key);
        request6.queryParam("token",var.token);
        Response response6=request6.get();
        response6.prettyPrint();
        Assert.assertEquals(response6.getStatusCode(),200);
        System.out.println("-----------------------------------------------------------------------------------------");

        //7-Archive or unarchive a list: "https://api.trello.com/1/lists/{id}/closed?key=APIKey&token=APIToken&value=True"
        RestAssured.baseURI="https://api.trello.com";
        RequestSpecification request7=RestAssured.given();
        request7.basePath("/1/lists/"+listID+"/closed");
        request7.queryParam("key",var.key);
        request7.queryParam("token",var.token);
        request7.queryParam("value","true");
        Response response7=request7.put();
        response7.prettyPrint();
        Assert.assertEquals(response7.getStatusCode(),200);
        System.out.println("-----------------------------------------------------------------------------------------");

        //8-Delete a Board: "https://api.trello.com/1/boards/{id}?key=APIKey&token=APIToken"
        RestAssured.baseURI="https://api.trello.com";
        RequestSpecification request8=RestAssured.given();
        request8.basePath("/1/boards/"+boardID);
        request8.queryParam("key",var.key);
        request8.queryParam("token",var.token);
        Response response8=request8.delete();
        response8.prettyPrint();
        Assert.assertEquals(response8.getStatusCode(),200);
        System.out.println("-----------------------------------------------------------------------------------------");

        //9-Delete an Organization: "https://api.trello.com/1/organizations/{id}?key=APIKey&token=APIToken"
        RestAssured.baseURI="https://api.trello.com";
        RequestSpecification request9=RestAssured.given();
        request9.basePath("/1/organizations/"+orgID);
        request9.queryParam("key",var.key);
        request9.queryParam("token",var.token);
        Response response9=request9.delete();
        response9.prettyPrint();
        Assert.assertEquals(response9.getStatusCode(),200);
        System.out.println("-----------------------------------------------------------------------------------------");



    }
}
