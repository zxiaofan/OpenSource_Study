/*
 * 文件名：SendMail.java
 * 版权：Copyright 2007-2015 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： SendMail.java
 * 修改人：yunhai
 * 修改时间：2015年8月12日
 * 修改内容：新增
 */

/**
 * TODO 163.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * <pre>
 * </pre>
 * 
 * @author     yunhai
 */
package SendMail163.business;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import SendMail163.model.vo.SendMailVo;

/**
 * Title: 使用javamail发送邮件,此实例可发送多附件
 * 
 * @version 1.0
 */
public class SendMailBusiness {
    /**
     * 多用途网际邮件扩充协议（MIME）,方法说明：发送邮件。
     * 
     * @Message 表示一个邮件，messgaes.getContent()返回一个Multipart对象。一个Multipart对象包含一个或多个BodyPart对象，来组成邮件的正文部分（包括附件）
     * @return 返回类型：boolean 成功为true，反之为false
     */
    public boolean sendMail(final SendMailVo sendMailVo, Vector file) {

        // 构造mail session
        Properties props = System.getProperties();
        String host = sendMailVo.getHost();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        // 登录验证并得到默认的对话对象
        Session session = null;
        // Authentication 证明，身份验证
        session = Session.getDefaultInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendMailVo.getUsername(), sendMailVo.getPassword());
            }
        });

        try {
            // 构造MimeMessage消息，并设定基本的值
            MimeMessage msg = new MimeMessage(session);
            String form = sendMailVo.getFrom();
            msg.setFrom(new InternetAddress(form));
            InternetAddress[] address = {new InternetAddress(sendMailVo.getTo())};
            msg.setRecipients(Message.RecipientType.TO, address);
            String subject = transferChinese(sendMailVo.getSubject());
            msg.setSubject(subject);

            // 构造Multipart(HTTP Multipart是一个文件上传组件)
            Multipart mp = new MimeMultipart();

            // 向Multipart添加正文
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setText(sendMailVo.getContent());
            // 向MimeMessage添加（Multipart代表正文）
            mp.addBodyPart(mbpContent);

            // 向Multipart添加附件
            Enumeration efile = file.elements();
            while (efile.hasMoreElements()) {
                MimeBodyPart mbpFile = new MimeBodyPart();
                String filename = efile.nextElement().toString();
                FileDataSource fds = new FileDataSource(filename);
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(fds.getName()); // 向MimeMessage添加（Multipart代表附件）
                mp.addBodyPart(mbpFile);
            }
            file.removeAllElements();

            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            msg.setSentDate(new Date());

            try {
                // 发送邮件
                Transport.send(msg);
            } catch (AuthenticationFailedException e) {
                // AuthenticationFailed (身份)验证失败,抛出用户名密码异常
                e.printStackTrace();
                System.out.println("用户名或密码错误！");
            }
            // Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            // 输出双重异常
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
            return false;
        }
        return true;
    }

    // 方法说明：把主题转换为中文
    public String transferChinese(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "UTF-8"), "UTF-8", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }

    // 方法说明：往附件组合中添加附件
    public void attachfile(Vector file, String fname) {
        file.addElement(fname);
    }
}