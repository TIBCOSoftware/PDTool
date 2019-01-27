
package com.compositesw.services.system.admin.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cacheConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cacheConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allOrNothing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="configured" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="incremental" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="storage" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="useDefaultCacheStorage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="mode" type="{http://www.compositesw.com/services/system/admin/resource}storageMode" minOccurs="0"/>
 *                   &lt;element name="bucketMode" type="{http://www.compositesw.com/services/system/admin/resource}bucketModeType" minOccurs="0"/>
 *                   &lt;element name="bucketProperties" type="{http://www.compositesw.com/services/system/admin/resource}bucketPropertiesType" minOccurs="0"/>
 *                   &lt;element name="dropCreateIdx" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="storageDataSourcePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="storageTargets" type="{http://www.compositesw.com/services/system/admin/resource}targetPathTypePairList" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="refresh" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="mode" type="{http://www.compositesw.com/services/system/admin/resource}refreshMode"/>
 *                   &lt;element name="schedule" type="{http://www.compositesw.com/services/system/admin/resource}schedule" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="expirationPeriod" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="firstRefreshCallback" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondRefreshCallback" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clearRule" type="{http://www.compositesw.com/services/system/admin/resource}clearRule" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cacheConfig", propOrder = {
    "allOrNothing",
    "configured",
    "enabled",
    "incremental",
    "storage",
    "refresh",
    "expirationPeriod",
    "firstRefreshCallback",
    "secondRefreshCallback",
    "clearRule"
})
public class CacheConfig {

    protected Boolean allOrNothing;
    protected Boolean configured;
    protected Boolean enabled;
    protected Boolean incremental;
    protected CacheConfig.Storage storage;
    protected CacheConfig.Refresh refresh;
    protected Long expirationPeriod;
    protected String firstRefreshCallback;
    protected String secondRefreshCallback;
    protected ClearRule clearRule;

    /**
     * Gets the value of the allOrNothing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllOrNothing() {
        return allOrNothing;
    }

    /**
     * Sets the value of the allOrNothing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllOrNothing(Boolean value) {
        this.allOrNothing = value;
    }

    /**
     * Gets the value of the configured property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConfigured() {
        return configured;
    }

    /**
     * Sets the value of the configured property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConfigured(Boolean value) {
        this.configured = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the incremental property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncremental() {
        return incremental;
    }

    /**
     * Sets the value of the incremental property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncremental(Boolean value) {
        this.incremental = value;
    }

    /**
     * Gets the value of the storage property.
     * 
     * @return
     *     possible object is
     *     {@link CacheConfig.Storage }
     *     
     */
    public CacheConfig.Storage getStorage() {
        return storage;
    }

    /**
     * Sets the value of the storage property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheConfig.Storage }
     *     
     */
    public void setStorage(CacheConfig.Storage value) {
        this.storage = value;
    }

    /**
     * Gets the value of the refresh property.
     * 
     * @return
     *     possible object is
     *     {@link CacheConfig.Refresh }
     *     
     */
    public CacheConfig.Refresh getRefresh() {
        return refresh;
    }

    /**
     * Sets the value of the refresh property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheConfig.Refresh }
     *     
     */
    public void setRefresh(CacheConfig.Refresh value) {
        this.refresh = value;
    }

    /**
     * Gets the value of the expirationPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getExpirationPeriod() {
        return expirationPeriod;
    }

    /**
     * Sets the value of the expirationPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setExpirationPeriod(Long value) {
        this.expirationPeriod = value;
    }

    /**
     * Gets the value of the firstRefreshCallback property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstRefreshCallback() {
        return firstRefreshCallback;
    }

    /**
     * Sets the value of the firstRefreshCallback property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstRefreshCallback(String value) {
        this.firstRefreshCallback = value;
    }

    /**
     * Gets the value of the secondRefreshCallback property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondRefreshCallback() {
        return secondRefreshCallback;
    }

    /**
     * Sets the value of the secondRefreshCallback property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondRefreshCallback(String value) {
        this.secondRefreshCallback = value;
    }

    /**
     * Gets the value of the clearRule property.
     * 
     * @return
     *     possible object is
     *     {@link ClearRule }
     *     
     */
    public ClearRule getClearRule() {
        return clearRule;
    }

    /**
     * Sets the value of the clearRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearRule }
     *     
     */
    public void setClearRule(ClearRule value) {
        this.clearRule = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="mode" type="{http://www.compositesw.com/services/system/admin/resource}refreshMode"/>
     *         &lt;element name="schedule" type="{http://www.compositesw.com/services/system/admin/resource}schedule" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "mode",
        "schedule"
    })
    public static class Refresh {

        @XmlElement(required = true)
        protected RefreshMode mode;
        protected Schedule schedule;

        /**
         * Gets the value of the mode property.
         * 
         * @return
         *     possible object is
         *     {@link RefreshMode }
         *     
         */
        public RefreshMode getMode() {
            return mode;
        }

        /**
         * Sets the value of the mode property.
         * 
         * @param value
         *     allowed object is
         *     {@link RefreshMode }
         *     
         */
        public void setMode(RefreshMode value) {
            this.mode = value;
        }

        /**
         * Gets the value of the schedule property.
         * 
         * @return
         *     possible object is
         *     {@link Schedule }
         *     
         */
        public Schedule getSchedule() {
            return schedule;
        }

        /**
         * Sets the value of the schedule property.
         * 
         * @param value
         *     allowed object is
         *     {@link Schedule }
         *     
         */
        public void setSchedule(Schedule value) {
            this.schedule = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="useDefaultCacheStorage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="mode" type="{http://www.compositesw.com/services/system/admin/resource}storageMode" minOccurs="0"/>
     *         &lt;element name="bucketMode" type="{http://www.compositesw.com/services/system/admin/resource}bucketModeType" minOccurs="0"/>
     *         &lt;element name="bucketProperties" type="{http://www.compositesw.com/services/system/admin/resource}bucketPropertiesType" minOccurs="0"/>
     *         &lt;element name="dropCreateIdx" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="storageDataSourcePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="storageTargets" type="{http://www.compositesw.com/services/system/admin/resource}targetPathTypePairList" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "useDefaultCacheStorage",
        "mode",
        "bucketMode",
        "bucketProperties",
        "dropCreateIdx",
        "storageDataSourcePath",
        "storageTargets"
    })
    public static class Storage {

        protected Boolean useDefaultCacheStorage;
        protected StorageMode mode;
        protected BucketModeType bucketMode;
        protected BucketPropertiesType bucketProperties;
        protected Boolean dropCreateIdx;
        protected String storageDataSourcePath;
        protected TargetPathTypePairList storageTargets;

        /**
         * Gets the value of the useDefaultCacheStorage property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isUseDefaultCacheStorage() {
            return useDefaultCacheStorage;
        }

        /**
         * Sets the value of the useDefaultCacheStorage property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setUseDefaultCacheStorage(Boolean value) {
            this.useDefaultCacheStorage = value;
        }

        /**
         * Gets the value of the mode property.
         * 
         * @return
         *     possible object is
         *     {@link StorageMode }
         *     
         */
        public StorageMode getMode() {
            return mode;
        }

        /**
         * Sets the value of the mode property.
         * 
         * @param value
         *     allowed object is
         *     {@link StorageMode }
         *     
         */
        public void setMode(StorageMode value) {
            this.mode = value;
        }

        /**
         * Gets the value of the bucketMode property.
         * 
         * @return
         *     possible object is
         *     {@link BucketModeType }
         *     
         */
        public BucketModeType getBucketMode() {
            return bucketMode;
        }

        /**
         * Sets the value of the bucketMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link BucketModeType }
         *     
         */
        public void setBucketMode(BucketModeType value) {
            this.bucketMode = value;
        }

        /**
         * Gets the value of the bucketProperties property.
         * 
         * @return
         *     possible object is
         *     {@link BucketPropertiesType }
         *     
         */
        public BucketPropertiesType getBucketProperties() {
            return bucketProperties;
        }

        /**
         * Sets the value of the bucketProperties property.
         * 
         * @param value
         *     allowed object is
         *     {@link BucketPropertiesType }
         *     
         */
        public void setBucketProperties(BucketPropertiesType value) {
            this.bucketProperties = value;
        }

        /**
         * Gets the value of the dropCreateIdx property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDropCreateIdx() {
            return dropCreateIdx;
        }

        /**
         * Sets the value of the dropCreateIdx property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDropCreateIdx(Boolean value) {
            this.dropCreateIdx = value;
        }

        /**
         * Gets the value of the storageDataSourcePath property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStorageDataSourcePath() {
            return storageDataSourcePath;
        }

        /**
         * Sets the value of the storageDataSourcePath property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStorageDataSourcePath(String value) {
            this.storageDataSourcePath = value;
        }

        /**
         * Gets the value of the storageTargets property.
         * 
         * @return
         *     possible object is
         *     {@link TargetPathTypePairList }
         *     
         */
        public TargetPathTypePairList getStorageTargets() {
            return storageTargets;
        }

        /**
         * Sets the value of the storageTargets property.
         * 
         * @param value
         *     allowed object is
         *     {@link TargetPathTypePairList }
         *     
         */
        public void setStorageTargets(TargetPathTypePairList value) {
            this.storageTargets = value;
        }

    }

}
