package com.ifenduo.coach.bean;
import java.io.Serializable;

/**
 * Created by edyang on 15/4/6.
 */
public class Notice  implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -372846364091939523L;

	private Long id;

    /** 案号(2015)九法民初字第00001号 */
    private String anhao;

    /** 开庭日期 */
    private String openCaseDate;

    /** 原告 */
    private String plaintiff;

    /** 被告 */
    private String defendant;

    /** 承办人 */
    private String agent;

    /** 开庭法庭 */
    private String fating;

    public String getFating() {
        return fating;
    }

    public void setFating(String fating) {
        this.fating = fating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnhao() {
        return anhao;
    }

    public void setAnhao(String anhao) {
        this.anhao = anhao;
    }

    public String getOpenCaseDate() {
        return openCaseDate;
    }

    public void setOpenCaseDate(String openCaseDate) {
        this.openCaseDate = openCaseDate;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
