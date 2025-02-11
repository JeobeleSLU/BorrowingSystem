public class Transaction implements XMLTemplate {
    String userId;
    String equipmentId;
    String equipmentName;
    int qty;
    String time;
    String type;

    /*

        <qty>2</qty>
        <equipmentName>Cisco</equipmentName>
        <time>220</time>
        <userId>223</userId>
        <equipmentId>224</equipmentId>
     */

    public Transaction(int qty,String equipmentName,String time, String userId,String equipmentId) {
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.qty = qty;
        this.time = time;
        this.type = type;
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
        };
    }

    /*
        return new StringBuilder()
                .append("type:").append(this.type).append(",")
                .append("id:").append(this.id).append(",")
                .append("name:").append(this.name).append(",")
                .append("quantity:").append(this.quantity).append(",")
                .append("Image").append(ImageHandler.encodeImageToBase64(imagePath)).append(",")
                .append("isAvailable:").append(this.isAvailable)
                .toString();
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
                .append("qty:").append(this.qty).append(",")
                .append("time:").append(this.time).toString();
    }
}
