package comum

import com.typesafe.config.ConfigFactory


 object Util {

  def msgRetorno(status:String,msg :String,dados:Any):Map[Any,Any]={
    Map("status"->status,"msg"->msg,dados -> dados)
  }

  def getEnv(key: String): String = ConfigFactory.load().getString(key)


}
