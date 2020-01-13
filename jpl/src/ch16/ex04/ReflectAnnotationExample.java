package ch16.ex04;

// http://pppurple.hatenablog.com/entry/2016/08/15/003240

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public class ReflectAnnotationExample {

    @FieldAnnotation
    public String name;

    @ConstructorAnnotaion
    public ReflectAnnotationExample(String name) {
        this.name = name;
    }

    @MethodAnnotation
    public String getName() {
        return name;
    }

    @Deprecated
    @ClassAnnotation
    public class DeprecatedClass {

    }

    @Target(FIELD)
    @Retention(RUNTIME)
    public @interface FieldAnnotation {

    }

    @Target(CONSTRUCTOR)
    @Retention(RUNTIME)
    public @interface ConstructorAnnotaion {

    }

    @Target(METHOD)
    @Retention(RUNTIME)
    public @interface MethodAnnotation {

    }

    @Target(TYPE)
    @Retention(RUNTIME)
    @Inherited
    public @interface ClassAnnotation {

    }

    // 以下追加
    @Retention(RUNTIME)
    @interface ClassInfo {
    	String created();
    	String createdBy();
    	String lastModified();
    	String lastModifiedBy();
    	Revision revision();
    }

    @interface Revision {
    	int major() default 1;
    	int minor() default 0;
    }

    @Retention(RUNTIME)
    public @interface BugsFixed {
    	String[] bugIDs();
    }

    @ClassInfo(
		created = "Jan 31 2005",
		createdBy = "James Goslig",
		lastModified = "Feb 9 2005",
		lastModifiedBy = "Ken Arnold",
		revision = @Revision(major = 3)
    )
    @BugsFixed(bugIDs = {"457605", "532456"})
    @ClassAnnotation
    public class AnnotationClassTest {

    }


    @ClassInfo(
		created = "Jan 31 2005",
		createdBy = "James Goslig",
		lastModified = "Feb 9 2005",
		lastModifiedBy = "Ken Arnold",
		revision = @Revision(major = 3)
    )
    @BugsFixed(bugIDs = {"457605", "532456"})
    @MethodAnnotation
    public void AnnotationMethodTest() {

    }
}