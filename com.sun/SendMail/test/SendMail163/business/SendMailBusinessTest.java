/*
 * 文件名：SendMailBusinessTest.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SendMailBusinessTest.java
 * 修改人：yunhai
 * 修改时间：2015年8月12日
 * 修改内容：新增
 */
package SendMail163.business;

import java.util.Vector;

import org.junit.Test;

import SendMail163.model.vo.SendMailVo;

/**
 * TODO 添加类的一句话简单描述.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author yunhai
 */
public class SendMailBusinessTest {

    /**
     * Test method for {@link SendMail163.business.SendMailBusiness#sendMail()}.
     */
    @Test
    public void testSendMail() {

        SendMailBusiness sendmail = new SendMailBusiness();
        SendMailVo sendMailVo = new SendMailVo();
        sendMailVo.setHost("smtp.ym.163.com");// smtp.mail.yahoo.com.cn //smtp.ym.163.com
        String name = "xxx@xxx.cf";
        sendMailVo.setUsername(name);
        sendMailVo.setPassword("zxiaofan.com");
        // 暂无法判断邮件是否被退回(收件人地址有误)
        sendMailVo.setTo("xxx@xxx.com");// 接收者
        sendMailVo.setFrom(name);// 发送者名字
        sendMailVo.setSubject("[主题]你好，这是测试邮件！");
        sendMailVo.setContent("[正文]你好,这是一个带多附件的测试！");
        // Vector 类提供了实现可增长数组的功能，随着更多元素加入其中，数组变的更大。在删除一些元素之后，数组变小。
        Vector file = new Vector();// 附件的文件集合
        sendmail.attachfile(file, "D:/attachment1.txt");
        sendmail.attachfile(file, "D:/test.bmp");
        boolean flag = sendmail.sendMail(sendMailVo, file);
        System.out.println("邮件发送状态：" + flag);
    }
}
