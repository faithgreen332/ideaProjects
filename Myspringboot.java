/**
 *	1.往 Set<Class<?>> primarySources 加进去主类
 	2.webApplicationType 设置成 SERVLET 类型
 	3.List<ApplicationContextInitializer<?>> initializers 里加进来 7 个
 	4.List<ApplicationListener<?>> listeners 加进来 9 个
 	5.mainApplicationClass 设置成主类
 */
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
		return new SpringApplication(primarySources).run(args);
	}


public ConfigurableApplicationContext run(String... args) {
		// 一个计时的，不重要
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		// 只初始化了一个空的默认引导上下文
		DefaultBootstrapContext bootstrapContext = createBootstrapContext();
		// 声明一个可配置的应用程序上下文
		ConfigurableApplicationContext context = null;
		// 配置awt之类用的，不重要
		configureHeadlessProperty();
		// 声明应用程序运行监听集合，往 listeners 里加一个 EventPublishingRunListener
		SpringApplicationRunListeners listeners = getRunListeners(args);
		// EventPublishingRunListener 在  new SpringApplication 不是有9个listeners嘛，针对ApplicationStartingEvent，这里取出来4个监听器
		// 这4个监听器都是实现 GenericApplicationLisener 的，并启动，他们分别是
		// 1.LoggingApplicationListener，初始化日志系统，如果用户没有自定义，就是 springboot 默认的 logback
		// 2.DelegatingApplicationListener,如果环境变量 context.lisener.class 配置了其他监听器，就委托给这些监听器执行，默认没有配置，所以什么都不干
		// 3.BackgroundPreinitializer,在后台线程中触发耗时任务的早起初始化，将IGNORE_BACKGROUNDPREINITIALIZER_PROPERTY_NAME 系统属性设置为true以禁用此机制并在前台进行初始化。这里跟了代码发现什么也没干，不重要。
		// 4.LiquibaseServiceLocatorApplicationListener,将liquibase ServiceLocator替换为使用Spring Boot可执行存档的版本。跟了代码发现什么也没干，不重要。
		listeners.starting(bootstrapContext, this.mainApplicationClass);
		try {
			// 初始化应用程序参数对象
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			// 这一步准备环境，这回监听的是PrepareEnvironment事件，在/META-INF/的 spring.factories 里有对应的处理器来处理(太复杂了捋不清楚)，并返回一个 StanderServletEnvironment对象，并初始化了一下几个属性
			// 1.propertySources， 把name 叫 servletConfigInitParams(目前是一个Object类型的占位符)、servletContextInitParams(目前是一个Object类型的占位符)、systemProperties、systemEnvironment、random(不知道是干什么的)、yaml文件里定义的所有属性值等等初始化
			// 2.propertyResolver,有一个 propertySources 的引用，还有好多的 Converters，暂时不知道干什么用
			ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
			// 配置忽略的BeanInfo，没有
			configureIgnoreBeanInfo(environment);
			// 控制台打印 spring 的哪个 banner，玩家可以自定义自己喜欢的 banner，网上有帖子，不重要
			Banner printedBanner = printBanner(environment);
			// 返回一个AnnotationConfigServletWebServerApplicationContexxt对象，主要关注 reader(AnnotatedBeanDefinitionReader)属性和 scanner 属性，reader属性初始化了五个处理器，scanner里初始化了Component、ManagedBean的两个includeFilters
			context = createApplicationContext();
			context.setApplicationStartup(this.applicationStartup);
			// 初始化了context里的beanFactory，加进来beanDifinitionMap里加紧来了主类（包含类注解的描述信息），singletonObjects里也加进来日志系统对象
			prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
			// 这一步厉害了，初始化所有的 bean ，初始化 tomcat 并启动
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}

	private DefaultBootstrapContext createBootstrapContext() {
		DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
		this.bootstrapRegistryInitializers.forEach((initializer) -> initializer.initialize(bootstrapContext));
		return bootstrapContext;
	}
