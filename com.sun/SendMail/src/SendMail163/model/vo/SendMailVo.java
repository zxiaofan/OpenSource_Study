/*
 * 文件名：SendMailVo.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SendMailVo.java
 * 修改人：yunhai
 * 修改时间：2015年8月12日
 * 修改内容：新增
 */
package SendMail163.model.vo;

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
public class SendMailVo {
    String to = "";// 收件人

    String from = "";// 发件人

    String host = "";// smtp主机

    String username = "";

    String password = "";

    String filename = "";// 附件文件名

    String subject = "";// 邮件主题

    String content = "";// 邮件正文

    /**
     * 设置to.
     * 
     * @return 返回to
     */
    public String getTo() {
        return to;
    }

    /**
     * 获取to.
     * 
     * @param to
     *            要设置的to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 设置from.
     * 
     * @return 返回from
     */
    public String getFrom() {
        return from;
    }

    /**
     * 获取from.
     * 
     * @param from
     *            要设置的from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 设置host.
     * 
     * @return 返回host
     */
    public String getHost() {
        return host;
    }

    /**
     * 获取host.
     * 
     * @param host
     *            要设置的host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 设置username.
     * 
     * @return 返回username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取username.
     * 
     * @param username
     *            要设置的username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 设置password.
     * 
     * @return 返回password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取password.
     * 
     * @param password
     *            要设置的password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置filename.
     * 
     * @return 返回filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 获取filename.
     * 
     * @param filename
     *            要设置的filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 设置subject.
     * 
     * @return 返回subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 获取subject.
     * 
     * @param subject
     *            要设置的subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 设置content.
     * 
     * @return 返回content
     */
    public String getContent() {
        return content;
    }

    /**
     * 获取content.
     * 
     * @param content
     *            要设置的content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
