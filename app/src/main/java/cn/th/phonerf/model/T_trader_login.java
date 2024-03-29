package cn.th.phonerf.model;

import java.util.Date;

public class T_trader_login extends  BaseEntity{
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.login_id
     *
     * @mbg.generated
     */
    private Integer login_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.app_uuid
     *
     * @mbg.generated
     */
    private Integer app_uuid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.login_login
     *
     * @mbg.generated
     */
    private String login_login;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.login_name
     *
     * @mbg.generated
     */
    private String login_name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.clear_flag
     *
     * @mbg.generated
     */
    private String clear_flag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.login_pw
     *
     * @mbg.generated
     */
    private String login_pw;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.role_id
     *
     * @mbg.generated
     */
    private String role_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_trader_login.last_time
     *
     * @mbg.generated
     */
    private Date last_time;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.login_id
     *
     * @return the value of t_trader_login.login_id
     *
     * @mbg.generated
     */
    public Integer getLogin_id() {
        return login_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.login_id
     *
     * @param login_id the value for t_trader_login.login_id
     *
     * @mbg.generated
     */
    public void setLogin_id(Integer login_id) {
        this.login_id = login_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.app_uuid
     *
     * @return the value of t_trader_login.app_uuid
     *
     * @mbg.generated
     */
    public Integer getApp_uuid() {
        return app_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.app_uuid
     *
     * @param app_uuid the value for t_trader_login.app_uuid
     *
     * @mbg.generated
     */
    public void setApp_uuid(Integer app_uuid) {
        this.app_uuid = app_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.login_login
     *
     * @return the value of t_trader_login.login_login
     *
     * @mbg.generated
     */
    public String getLogin_login() {
        return login_login;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.login_login
     *
     * @param login_login the value for t_trader_login.login_login
     *
     * @mbg.generated
     */
    public void setLogin_login(String login_login) {
        this.login_login = login_login == null ? null : login_login.trim();
    } 

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.login_name
     *
     * @return the value of t_trader_login.login_name
     *
     * @mbg.generated
     */
    public String getLogin_name() {
        return login_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.login_name
     *
     * @param login_name the value for t_trader_login.login_name
     *
     * @mbg.generated
     */
    public void setLogin_name(String login_name) {
        this.login_name = login_name == null ? null : login_name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.clear_flag
     *
     * @return the value of t_trader_login.clear_flag
     *
     * @mbg.generated
     */
    public String getClear_flag() {
        return clear_flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.clear_flag
     *
     * @param clear_flag the value for t_trader_login.clear_flag
     *
     * @mbg.generated
     */
    public void setClear_flag(String clear_flag) {
        this.clear_flag = clear_flag == null ? null : clear_flag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.login_pw
     *
     * @return the value of t_trader_login.login_pw
     *
     * @mbg.generated
     */
    public String getLogin_pw() {
        return login_pw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.login_pw
     *
     * @param login_pw the value for t_trader_login.login_pw
     *
     * @mbg.generated
     */
    public void setLogin_pw(String login_pw) {
        this.login_pw = login_pw == null ? null : login_pw.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.role_id
     *
     * @return the value of t_trader_login.role_id
     *
     * @mbg.generated
     */
    public String getRole_id() {
        return role_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.role_id
     *
     * @param role_id the value for t_trader_login.role_id
     *
     * @mbg.generated
     */
    public void setRole_id(String role_id) {
        this.role_id = role_id == null ? null : role_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_trader_login.last_time
     *
     * @return the value of t_trader_login.last_time
     *
     * @mbg.generated
     */
    public Date getLast_time() {
        return last_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_trader_login.last_time
     *
     * @param last_time the value for t_trader_login.last_time
     *
     * @mbg.generated
     */
    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }
}