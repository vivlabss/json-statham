/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.reflect;

import com.lckymn.kevin.common.util.CommonConstants;
import com.lckymn.kevin.jsonstatham.core.JsonStathamInAction;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonJsonArrayConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonOrderedJsonObjectConvertibleCreator;
import com.lckymn.kevin.jsonstatham.core.convertible.OrgJsonUnorderedJsonObjectConvertibleCreator;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-12)
 */
public final class ReflectionJsonStathams
{
	private ReflectionJsonStathams()
	{
		throw new IllegalStateException(getClass() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	public static JsonStathamInAction newJsonStathamInAction(
			final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
			final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter)
	{
		return new JsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);
	}

	/**
	 * Returns JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 * The ReflectionJavaToJsonConverter object is created with OrgJsonOrderedJsonObjectConvertibleCreator,
	 * OrgJsonJsonArrayConvertibleCreator, KnownDataStructureTypeProcessorDecider,
	 * KnownObjectReferenceTypeProcessorDecider and OneProcessorForKnownTypeDecider.
	 * 
	 * @return JsonStathamInAction object created with ReflectionJavaToJsonConverter and ReflectionJsonToJavaConverter.
	 */
	public static JsonStathamInAction newReflectionJsonStathamInAction()
	{
		final OrgJsonOrderedJsonObjectConvertibleCreator orderedJsonObjectConvertibleCreator =
			new OrgJsonOrderedJsonObjectConvertibleCreator();
		final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
			new OrgJsonJsonArrayConvertibleCreator();
		return newJsonStathamInAction(new ReflectionJavaToJsonConverter(orderedJsonObjectConvertibleCreator,
				orgJsonJsonArrayConvertibleCreator, new KnownDataStructureTypeProcessorDecider(),
				new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider()),
				new ReflectionJsonToJavaConverter(orderedJsonObjectConvertibleCreator,
						orgJsonJsonArrayConvertibleCreator));
	}

	public static JsonStathamInAction newUnorderedReflectionJsonStathamInAction()
	{
		final OrgJsonUnorderedJsonObjectConvertibleCreator orgJsonUnorderedJsonObjectConvertibleCreator =
			new OrgJsonUnorderedJsonObjectConvertibleCreator();
		final OrgJsonJsonArrayConvertibleCreator orgJsonJsonArrayConvertibleCreator =
			new OrgJsonJsonArrayConvertibleCreator();
		return newJsonStathamInAction(new ReflectionJavaToJsonConverter(orgJsonUnorderedJsonObjectConvertibleCreator,
				orgJsonJsonArrayConvertibleCreator, new KnownDataStructureTypeProcessorDecider(),
				new KnownObjectReferenceTypeProcessorDecider(), new OneProcessorForKnownTypeDecider()),
				new ReflectionJsonToJavaConverter(orgJsonUnorderedJsonObjectConvertibleCreator,
						orgJsonJsonArrayConvertibleCreator));
	}
}