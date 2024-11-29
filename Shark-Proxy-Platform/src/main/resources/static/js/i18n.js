// I18N 国际化
let language = (navigator.language.includes('zh')) ? 'zh' : 'en'

const languageMap = {
    zh: '中文',
    en: 'English'
}

// Ready translated locale messages
const messages = {
    // 英文
    en: {
        header: {
            title: 'Sage - Free Proxy IP Library',
            menu: {
                index: 'Index',
                api: 'API Document'
            }
        },
        main: {
            table: {
                emptyText: 'No Data',
                port: 'Port',
                country: 'Country',
                province: 'Province',
                respTime: 'ResponseTime(S)',
                type: 'Protocol',
                anonymous: 'Anonymous',
                anonymous_0: 'Transparent',
                anonymous_1: 'Anonymous',
                anonymous_2: 'Elite',
                supportHttps: 'HTTPS',
                supportPost: 'POST',
                survivalRate: 'SurvivalRate',
                lastCheckTime: 'LastCheckTime',
                rowClickSuccess: 'Copy Successful',
                rowClickError: 'Copy Failed'
            }
        },
        footer: {
            title: 'Disclaimer',
            content: 'This site\'s free agent IP equal collection self-connection network, this site\'s non-free agent\'s effective liability, legal use free agent, use free agent IP\'s legal responsibility and this site.'
        }
    },
    // 简体中文
    zh: {
        header: {
            title: 'Sage - 免费代理IP库',
            menu: {
                index: '首页',
                api: 'API文档'
            }
        },
        main: {
            table: {
                emptyText: '暂无数据',
                port: '端口',
                country: '国家',
                province: '省',
                respTime: '响应时间(秒)',
                type: '协议',
                anonymous: '匿名度',
                anonymous_0: '透明',
                anonymous_1: '普匿',
                anonymous_2: '高匿',
                supportHttps: 'HTTPS',
                supportPost: 'POST',
                survivalRate: '存活率',
                lastCheckTime: '最后验证时间',
                rowClickSuccess: '复制成功',
                rowClickError: '复制失败'
            }
        },
        footer: {
            title: '免责声明',
            content: '本站免费代理IP均收集自互联网，本站不对免费代理的有效性负责，请合法使用免费代理，由用户使用免费代理IP带来的法律责任与本站无关。'
        }
    }
}

// Create VueI18n instance with options
const i18n = new VueI18n({
    locale: language, // set locale
    messages, // set locale messages
})
