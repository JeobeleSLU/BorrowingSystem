package Common.Model;
import Common.Utilities.XMLTemplate;
//test
public class Transaction implements XMLTemplate {
    String userId;
    String equipmentId;
    String equipmentName;
    int qty;
    String time;
    String date;
    /*
        <qty>2</qty>
        <equipmentName>Cisco</equipmentName>
        <time>220</time>
        <userId>223</userId>
        <equipmentId>224</equipmentId>
     */
    public Transaction(int qty, String equipmentName, String date, String time, String userId, String equipmentId) {
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.qty = qty;
        this.time = time;
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEquipmentId() {
        return equipmentId;
    }
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    /*
        return new String[]{
                    "type",
                    "id",
                    "name",
                    "quantity"
                    ,"image",
                    "isAvailable"
            };
        }
     */
    @Override
    public String[] getDataMembers() {
        return new String[] {
                "userId",
                "equipmentId",
                "equipmentName",
                "qty",
                "time"
                ,"date"
        };
    }
    /*
        "userId",
                "equipmentId",
                "equipmentName",
                "qty",
                "time"
     */
    /*
    String userId, String equipmentId, String equipmentName, int qty, String time
     */
    @Override
    public String getAllValues() {
        return new StringBuilder()
                .append("userId:").append(this.userId).append(",")
                .append("equipmentId:").append(this.equipmentId).append(",")
                .append("equipmentName:").append(this.equipmentName).append(",")
                .append("time:").append(this.time).append(",")
                .append("date:").append(this.date).append(",")
                .append("qty:").append(this.qty).append(",")
                .toString();
    }
}
