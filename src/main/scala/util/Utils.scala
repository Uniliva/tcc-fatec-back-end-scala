package util



object Utils {

  def msgRetorno(status:String,msg :String,dados:Any):Map[Any,Any]={
    Map("status"->status,"msg"->msg,dados -> dados)
  }

}
