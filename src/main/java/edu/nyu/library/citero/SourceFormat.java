package edu.nyu.library.citero;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hab278
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
/**
 * This marker annotation should be used in any format that Citero should
 * be able to convert from. To do this, it is recommended to have a private
 * doImport() method that is called by the constructor.
 *
 * This annotations has its Retention value set to RetentionPolicy.RUNTIME
 * and its Target value set to ElementType.Type.
 * @author hab278
 *
 */
public @interface SourceFormat {
}
