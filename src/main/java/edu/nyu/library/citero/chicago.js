// this is a locale bundle
var locale = {"en-US":"<locale xml:lang=\"en\" xmlns=\"http://purl.org/net/xbiblio/csl\">  <style-options punctuation-in-quote=\"true\"/>  <date form=\"text\">    <date-part name=\"month\" suffix=\" \"/>    <date-part name=\"day\" suffix=\", \"/>    <date-part name=\"year\"/>  </date>  <date form=\"numeric\">    <date-part name=\"year\"/>    <date-part name=\"month\" form=\"numeric\" prefix=\"-\" range-delimiter=\"/\"/>    <date-part name=\"day\" prefix=\"-\" range-delimiter=\"/\"/>  </date>  <terms>    <term name=\"document-number-label\">No.</term>    <term name=\"document-number-authority-suffix\">Doc.</term>    <term name=\"un-sales-number-label\">U.N. Sales No.</term>    <term name=\"collection-number-label\">No.</term>    <term name=\"open-quote\">\u201c</term>    <term name=\"close-quote\">\u201d</term>    <term name=\"open-inner-quote\">\u2018</term>    <term name=\"close-inner-quote\">\u2019</term>    <term name=\"ordinal-01\">st</term>    <term name=\"ordinal-02\">nd</term>    <term name=\"ordinal-03\">rd</term>    <term name=\"ordinal-04\">th</term>    <term name=\"long-ordinal-01\">first</term>    <term name=\"long-ordinal-02\">second</term>    <term name=\"long-ordinal-03\">third</term>    <term name=\"long-ordinal-04\">fourth</term>    <term name=\"long-ordinal-05\">fifth</term>    <term name=\"long-ordinal-06\">sixth</term>    <term name=\"long-ordinal-07\">seventh</term>    <term name=\"long-ordinal-08\">eighth</term>    <term name=\"long-ordinal-09\">ninth</term>    <term name=\"long-ordinal-10\">tenth</term>    <term name=\"at\">at</term>    <term name=\"in\">in</term>    <term name=\"ibid\">ibid</term>    <term name=\"accessed\">accessed</term>    <term name=\"retrieved\">retrieved</term>    <term name=\"from\">from</term>    <term name=\"forthcoming\">forthcoming</term>    <term name=\"references\">      <single>reference</single>      <multiple>references</multiple>    </term>    <term name=\"references\" form=\"short\">      <single>ref</single>      <multiple>refs</multiple>    </term>    <term name=\"no date\">n.d.</term>    <term name=\"and\">and</term>    <term name=\"et-al\">et al.</term>    <term name=\"interview\">interview</term>    <term name=\"letter\">letter</term>    <term name=\"anonymous\">anonymous</term>    <term name=\"anonymous\" form=\"short\">anon.</term>    <term name=\"and others\">and others</term>    <term name=\"in press\">in press</term>    <term name=\"online\">online</term>    <term name=\"cited\">cited</term>    <term name=\"internet\">internet</term>    <term name=\"presented at\">presented at the</term>    <term name=\"ad\">AD</term>    <term name=\"bc\">BC</term>    <term name=\"season-01\">Spring</term>    <term name=\"season-02\">Summer</term>    <term name=\"season-03\">Autumn</term>    <term name=\"season-04\">Winter</term>    <term name=\"with\">with</term>        <!-- CATEGORIES -->    <term name=\"anthropology\">anthropology</term>    <term name=\"astronomy\">astronomy</term>    <term name=\"biology\">biology</term>    <term name=\"botany\">botany</term>    <term name=\"chemistry\">chemistry</term>    <term name=\"engineering\">engineering</term>    <term name=\"generic-base\">generic base</term>    <term name=\"geography\">geography</term>    <term name=\"geology\">geology</term>    <term name=\"history\">history</term>    <term name=\"humanities\">humanities</term>    <term name=\"literature\">literature</term>    <term name=\"math\">math</term>    <term name=\"medicine\">medicine</term>    <term name=\"philosophy\">philosophy</term>    <term name=\"physics\">physics</term>    <term name=\"psychology\">psychology</term>    <term name=\"sociology\">sociology</term>    <term name=\"science\">science</term>    <term name=\"political_science\">political science</term>    <term name=\"social_science\">social science</term>    <term name=\"theology\">theology</term>    <term name=\"zoology\">zoology</term>        <!-- LONG LOCATOR FORMS -->    <term name=\"book\">      <single>book</single>      <multiple>books</multiple>    </term>    <term name=\"chapter\">      <single>chapter</single>      <multiple>chapters</multiple>    </term>    <term name=\"column\">      <single>column</single>      <multiple>columns</multiple>    </term>    <term name=\"figure\">      <single>figure</single>      <multiple>figures</multiple>    </term>    <term name=\"folio\">      <single>folio</single>      <multiple>folios</multiple>    </term>    <term name=\"issue\">      <single>number</single>      <multiple>numbers</multiple>    </term>    <term name=\"line\">      <single>line</single>      <multiple>lines</multiple>    </term>    <term name=\"note\">      <single>note</single>      <multiple>notes</multiple>    </term>    <term name=\"opus\">      <single>opus</single>      <multiple>opera</multiple>    </term>    <term name=\"page\">      <single>page</single>      <multiple>pages</multiple>    </term>    <term name=\"paragraph\">      <single>paragraph</single>      <multiple>paragraph</multiple>    </term>    <term name=\"part\">      <single>part</single>      <multiple>parts</multiple>    </term>    <term name=\"section\">      <single>section</single>      <multiple>sections</multiple>    </term>    <term name=\"volume\">      <single>volume</single>      <multiple>volumes</multiple>    </term>    <term name=\"edition\">      <single>edition</single>      <multiple>editions</multiple>    </term>    <term name=\"verse\">      <single>verse</single>      <multiple>verses</multiple>    </term>    <term name=\"sub verbo\">      <single>sub verbo</single>      <multiple>s.vv</multiple>    </term>        <!-- SHORT LOCATOR FORMS -->    <term name=\"book\" form=\"short\">bk.</term>    <term name=\"chapter\" form=\"short\">chap.</term>    <term name=\"column\" form=\"short\">col.</term>    <term name=\"figure\" form=\"short\">fig.</term>    <term name=\"folio\" form=\"short\">f.</term>    <term name=\"issue\" form=\"short\">no.</term>    <term name=\"opus\" form=\"short\">op.</term>    <term name=\"page\" form=\"short\">      <single>p.</single>      <multiple>pp.</multiple>    </term>    <term name=\"paragraph\" form=\"short\">para.</term>    <term name=\"part\" form=\"short\">pt.</term>    <term name=\"section\" form=\"short\">sec.</term>    <term name=\"sub verbo\" form=\"short\">      <single>s.v.</single>      <multiple>s.vv.</multiple>    </term>    <term name=\"verse\" form=\"short\">      <single>v.</single>      <multiple>vv.</multiple>    </term>    <term name=\"volume\" form=\"short\">    	<single>vol.</single>    	<multiple>vols.</multiple>    </term>    <term name=\"edition\">edition</term>    <term name=\"edition\" form=\"short\">ed.</term>        <!-- SYMBOL LOCATOR FORMS -->    <term name=\"paragraph\" form=\"symbol\">      <single>¶</single>      <multiple>¶¶</multiple>    </term>    <term name=\"section\" form=\"symbol\">      <single>§</single>      <multiple>§§</multiple>    </term>        <!-- LONG ROLE FORMS -->    <term name=\"author\">      <single></single>      <multiple></multiple>    </term>    <term name=\"editor\">      <single>editor</single>      <multiple>editors</multiple>    </term>    <term name=\"translator\">      <single>translator</single>      <multiple>translators</multiple>    </term>        <!-- SHORT ROLE FORMS -->    <term name=\"author\" form=\"short\">      <single></single>      <multiple></multiple>    </term>    <term name=\"editor\" form=\"short\">      <single>ed.</single>      <multiple>eds.</multiple>    </term>    <term name=\"translator\" form=\"short\">      <single>tran.</single>      <multiple>trans.</multiple>    </term>        <!-- VERB ROLE FORMS -->    <term name=\"editor\" form=\"verb\">edited by</term>    <term name=\"translator\" form=\"verb\">translated by</term>    <term name=\"recipient\" form=\"verb\">to</term>    <term name=\"interviewer\" form=\"verb\">interview by</term>        <!-- SHORT VERB ROLE FORMS -->    <term name=\"editor\" form=\"verb-short\">ed.</term>    <term name=\"translator\" form=\"verb-short\">trans.</term>        <!-- LONG MONTH FORMS -->    <term name=\"month-01\">January</term>    <term name=\"month-02\">February</term>    <term name=\"month-03\">March</term>    <term name=\"month-04\">April</term>    <term name=\"month-05\">May</term>    <term name=\"month-06\">June</term>    <term name=\"month-07\">July</term>    <term name=\"month-08\">August</term>    <term name=\"month-09\">September</term>    <term name=\"month-10\">October</term>    <term name=\"month-11\">November</term>    <term name=\"month-12\">December</term>        <!-- SHORT MONTH FORMS -->    <term name=\"month-01\" form=\"short\">Jan.</term>    <term name=\"month-02\" form=\"short\">Feb.</term>    <term name=\"month-03\" form=\"short\">Mar.</term>    <term name=\"month-04\" form=\"short\">Apr.</term>	<term name=\"month-05\" form=\"short\">May</term>    <term name=\"month-06\" form=\"short\">Jun.</term>    <term name=\"month-07\" form=\"short\">Jul.</term>    <term name=\"month-08\" form=\"short\">Aug.</term>    <term name=\"month-09\" form=\"short\">Sep.</term>    <term name=\"month-10\" form=\"short\">Oct.</term>    <term name=\"month-11\" form=\"short\">Nov.</term>    <term name=\"month-12\" form=\"short\">Dec.</term>  </terms></locale>"};
 
// this is a style from demo.html
var chicago_author_date = "<style       xmlns=\"http://purl.org/net/xbiblio/csl\"      class=\"in-text\"   default-locale=\"en-US-x-sort-ja-alalc97-x-sec-en\">  <!-- BOGUS COMMENT -->  <info>    <title>Chicago Manual of Style (Author-Date format)</title>    <id>http://www.zotero.org/styles/chicago-author-date</id>    <link href=\"http://www.zotero.org/styles/chicago-author-date\" />    <author>      <name>Julian Onions</name>      <email>julian.onions@gmail.com</email>    </author>    <category term=\"author-date\" />    <category term=\"generic-base\" />    <updated />    <summary>The author-date variant of the Chicago style</summary>    <link href=\"http://www.chicagomanualofstyle.org/tools_citationguide.html\" rel=\"documentation\" />  </info> <macro name=\"secondary-contributors\">    <choose>      <if match=\"none\" type=\"chapter\">        <group delimiter=\". \">          <choose>            <if variable=\"author\">              <names variable=\"editor\">                <label form=\"verb-short\" prefix=\" \" suffix=\". \" text-case=\"capitalize-first\" />                <name and=\"text\" delimiter=\", \" />              </names>            </if>          </choose>          <choose>            <if match=\"any\" variable=\"author editor\">              <names variable=\"translator\">                <label form=\"verb-short\" prefix=\" \" suffix=\". \" text-case=\"capitalize-first\" />                <name and=\"text\" delimiter=\", \" />              </names>            </if>          </choose>        </group>      </if>    </choose>  </macro>  <macro name=\"container-contributors\">    <choose>      <if type=\"chapter\">        <group delimiter=\", \" prefix=\",\">          <choose>            <if variable=\"author\">              <names variable=\"editor\">                <label form=\"verb-short\" prefix=\" \" suffix=\". \" text-case=\"lowercase\" />                <name and=\"text\" delimiter=\", \" />              </names>            </if>          </choose>          <choose>            <if match=\"any\" variable=\"author editor\">              <names variable=\"translator\">                <label form=\"verb-short\" prefix=\" \" suffix=\". \" text-case=\"lowercase\" />                <name and=\"text\" delimiter=\", \" />              </names>            </if>          </choose>        </group>      </if>    </choose>  </macro>  <macro name=\"anon\">    <choose>      <if match=\"none\" variable=\"author editor translator\">        <text form=\"short\" term=\"anonymous\" text-case=\"capitalize-first\" />      </if>    </choose>  </macro>  <macro name=\"editor\">    <names variable=\"editor\">      <name and=\"text\" delimiter=\", \" delimiter-precedes-last=\"always\" name-as-sort-order=\"first\" sort-separator=\", \" />      <label form=\"short\" prefix=\", \" suffix=\".\" />    </names>  </macro>  <macro name=\"translator\">    <names variable=\"translator\">      <name and=\"text\" delimiter=\", \" delimiter-precedes-last=\"always\" name-as-sort-order=\"first\" sort-separator=\", \" />      <label form=\"verb-short\" prefix=\", \" suffix=\".\" />    </names>  </macro>  <macro name=\"recipient\">    <choose>      <if type=\"personal_communication\">        <choose>          <if variable=\"genre\">            <text text-case=\"capitalize-first\" variable=\"genre\" />          </if>          <else>            <text term=\"letter\" text-case=\"capitalize-first\" />          </else>        </choose>      </if>    </choose>    <names delimiter=\", \" variable=\"recipient\">      <label form=\"verb\" prefix=\" \" suffix=\" \" text-case=\"lowercase\" />      <name and=\"text\" delimiter=\", \" />    </names>  </macro>  <macro name=\"contributors\">    <names variable=\"author\">      <name and=\"text\" delimiter=\", \" delimiter-precedes-last=\"always\" name-as-sort-order=\"first\" sort-separator=\", \" />      <label form=\"verb-short\" prefix=\", \" suffix=\".\" text-case=\"lowercase\" />      <substitute>        <text macro=\"editor\" />        <text macro=\"translator\" />      </substitute>    </names>    <text macro=\"anon\" />    <text macro=\"recipient\" />  </macro>  <macro name=\"contributors-short\">    <names variable=\"author\">      <name and=\"text\" delimiter=\", \" form=\"short\" />      <substitute>        <names variable=\"editor\" />        <names variable=\"translator\" />      </substitute>    </names>    <text macro=\"anon\" />  </macro>  <macro name=\"interviewer\">    <names delimiter=\", \" variable=\"interviewer\">      <label form=\"verb\" prefix=\" \" suffix=\" \" text-case=\"capitalize-first\" />      <name and=\"text\" delimiter=\", \" />    </names>  </macro>  <macro name=\"archive\">    <group delimiter=\". \">      <text text-case=\"capitalize-first\" variable=\"archive_location\" />      <text variable=\"archive\" />      <text variable=\"archive-place\" />    </group>  </macro>  <macro name=\"access\">    <group delimiter=\". \">      <choose>        <if match=\"any\" type=\"graphic report\">          <text macro=\"archive\" />        </if>        <else-if match=\"none\" type=\"book thesis chapter article-journal article-newspaper article-magazine\">          <text macro=\"archive\" />        </else-if>      </choose>      <text prefix=\"doi:\" variable=\"DOI\" />      <text variable=\"URL\" />    </group>  </macro>  <macro name=\"title\">    <choose>      <if match=\"none\" variable=\"title\">        <choose>          <if match=\"none\" type=\"personal_communication\">            <text text-case=\"capitalize-first\" variable=\"genre\" />          </if>        </choose>      </if>      <else-if type=\"book\">        <text font-style=\"italic\" variable=\"title\" />      </else-if>      <else>        <text variable=\"title\" />      </else>    </choose>  </macro>  <macro name=\"edition\">    <choose>      <if match=\"any\" type=\"book chapter\">        <choose>          <if is-numeric=\"edition\">            <group delimiter=\" \">              <number form=\"ordinal\" variable=\"edition\" />              <text form=\"short\" suffix=\".\" term=\"edition\" />            </group>          </if>          <else>            <text suffix=\".\" variable=\"edition\" />          </else>        </choose>      </if>    </choose>  </macro>  <macro name=\"locators\">    <choose>      <if type=\"article-journal\">        <text prefix=\" \" variable=\"volume\" />        <text prefix=\", no. \" variable=\"issue\" />      </if>      <else-if type=\"book\">        <group delimiter=\". \" prefix=\". \">          <group>            <text form=\"short\" suffix=\". \" term=\"volume\" text-case=\"capitalize-first\" />            <number form=\"numeric\" variable=\"volume\" />          </group>          <group>            <number form=\"numeric\" variable=\"number-of-volumes\" />            <text form=\"short\" plural=\"true\" prefix=\" \" suffix=\".\" term=\"volume\" />          </group>        </group>      </else-if>    </choose>  </macro>  <macro name=\"locators-chapter\">    <choose>      <if type=\"chapter\">        <group prefix=\", \">          <text suffix=\":\" variable=\"volume\" />          <text variable=\"page\" />        </group>      </if>    </choose>  </macro>  <macro name=\"locators-article\">    <choose>      <if type=\"article-newspaper\">        <group delimiter=\", \" prefix=\", \">          <group>            <text suffix=\" \" variable=\"edition\" />            <text prefix=\" \" term=\"edition\" />          </group>          <group>            <text form=\"short\" suffix=\". \" term=\"section\" />            <text variable=\"section\" />          </group>        </group>      </if>      <else-if type=\"article-journal\">        <text prefix=\": \" variable=\"page\" />      </else-if>    </choose>  </macro>  <macro name=\"point-locators\">    <group>      <choose>        <if locator=\"page\" match=\"none\">          <label form=\"short\" strip-periods=\"false\" suffix=\" \" variable=\"locator\" />        </if>      </choose>      <text variable=\"locator\" />    </group>  </macro>  <macro name=\"container-prefix\">    <text term=\"in\" text-case=\"capitalize-first\" />  </macro>  <macro name=\"container-title\">    <choose>      <if type=\"chapter\">        <text macro=\"container-prefix\" suffix=\" \" />      </if>    </choose>    <text font-style=\"italic\" variable=\"container-title\" form=\"short\"/>  </macro>  <macro name=\"publisher\">    <group delimiter=\": \">      <text variable=\"publisher-place\" />      <text variable=\"publisher\" />    </group>  </macro>  <macro name=\"date\">    <date variable=\"issued\" form=\"text\" date-parts=\"year\"><date-part name=\"year\"/></date>  </macro>  <macro name=\"day-month\">    <date variable=\"issued\">      <date-part name=\"month\" />      <date-part name=\"day\" prefix=\" \" />    </date>  </macro>  <macro name=\"collection-title\">    <text variable=\"collection-title\" />    <text prefix=\" \" variable=\"collection-number\" />  </macro>  <macro name=\"event\">    <group>      <text suffix=\" \" term=\"presented at\" />      <text variable=\"event\" />    </group>  </macro>  <macro name=\"description\">    <group delimiter=\". \">      <text macro=\"interviewer\" />      <text text-case=\"capitalize-first\" variable=\"medium\" />    </group>    <choose>      <if match=\"none\" variable=\"title\"> </if>      <else-if type=\"thesis\"> </else-if>      <else>        <text prefix=\". \" text-case=\"capitalize-first\" variable=\"genre\" />      </else>    </choose>  </macro>  <macro name=\"issue\">    <choose>      <if type=\"article-journal\">        <text macro=\"day-month\" prefix=\" (\" suffix=\")\" />      </if>      <else-if type=\"speech\">        <group delimiter=\", \" prefix=\" \">          <text macro=\"event\" />          <text macro=\"day-month\" />          <text variable=\"event-place\" />        </group>      </else-if>      <else-if match=\"any\" type=\"article-newspaper article-magazine\">        <text macro=\"day-month\" prefix=\", \" />      </else-if>      <else>        <group delimiter=\", \" prefix=\". \">          <choose>            <if type=\"thesis\">              <text text-case=\"capitalize-first\" variable=\"genre\" />            </if>          </choose>          <text macro=\"publisher\" />          <text macro=\"day-month\" />        </group>      </else>    </choose>  </macro>  <citation          disambiguate-add-givenname=\"true\"         disambiguate-add-names=\"true\"         disambiguate-add-year-suffix=\"true\"         et-al-min=\"4\"         et-al-subsequent-min=\"4\"         et-al-subsequent-use-first=\"1\"         et-al-use-first=\"1\">    <layout text-decoration=\"underline\" delimiter=\"; \" prefix=\"(\" suffix=\")\">      <group delimiter=\", \">        <group delimiter=\" \">          <text macro=\"contributors-short\" />          <text macro=\"date\" />        </group>        <text macro=\"point-locators\" />      </group>    </layout>  </citation>  <bibliography          entry-spacing=\"0\"         et-al-min=\"11\"         et-al-use-first=\"7\"         hanging-indent=\"true\"         subsequent-author-substitute=\"---\">    <sort>      <key macro=\"contributors\" />      <key variable=\"issued\" />    </sort>    <layout suffix=\".\">      <text macro=\"contributors\" suffix=\". \" />      <text macro=\"date\" suffix=\". \" />      <text macro=\"title\" />      <text macro=\"description\"/>      <text macro=\"secondary-contributors\" prefix=\". \" />      <text macro=\"container-title\" prefix=\". \"/>      <text macro=\"container-contributors\" />      <text macro=\"locators-chapter\" />      <text macro=\"edition\" prefix=\". \" />      <text macro=\"locators\" />      <text macro=\"collection-title\" prefix=\". \" />      <text macro=\"issue\" />      <text macro=\"locators-article\" />      <text macro=\"access\" prefix=\". \" />    </layout>  </bibliography></style>";
 
 
// this is the bibliography data
var bibdata = {
    "ITEM-1": {
	"id": "ITEM-1",
	"title":"Boundaries of Dissent: Protest and State Power in the Media Age",
	"author": [
	    {
		"family": "D'Arcus",
		"given": "Bruce",
		"static-ordering": false
	    }
	],
        "note":"The apostrophe in Bruce's name appears in proper typeset form.",
	"publisher": "Routledge",
	"issued": {
	    "date-parts":[
		[2005, 11, 22]
	    ]
	},
	"type": "book"
    }
};

var data = {
		"ITEM-1": {
			"id": "ITEM-1",
			"title":"Boundaries of Dissent: Protest and State Power in the Media Age",
			"author": [
				{
					"family": "D'Arcus",
					"given": "Bruce",
					"static-ordering": false
				}
			],
	        "note":"The apostrophe in Bruce's name appears in proper typeset form.",
			"publisher": "Routledge",
	        "publisher-place": "New York",
			"issued": {
				"date-parts":[
					[2006]
				]
			},
			"type": "book"
		},
}
 
 
// This defines the mechanism by which we get hold of the relevant data for
// the locale and the bibliography. 
// 
// In this case, they are pretty trivial, just returning the data which is
// embedded above. In practice, this might involving retrieving the data from
// a standard URL, for instance. 
var sys = {
    retrieveItem: function(id){
        return data[id];
    },
 
    retrieveLocale: function(lang){
        return locale[lang];
    }
}
 
 
// instantiate the citeproc object
var citeproc = new CSL.Engine( sys, chicago_author_date );
var my_ids = [
              "ITEM-1"
              ]
citeproc.updateItems( my_ids );
 
// This is the citation object. Here, we have hard-coded this, so it will only
// work with the correct HTML. 
var citation_object = 
    {
        // items that are in a citation that we want to add. in this case,
        // there is only one citation object, and we know where it is in
        // advance. 
        "citationItems": [
            {
                "id": "ITEM-1"
            }
        ],
        // properties -- count up from 0
        "properties": {
            "noteIndex": 0
        }
          
    }
 
function get_formatted_citation(){
    // this returns an in-text citation as a string, as well as adding it to
    // the bibliography, which will be generated later. The method returns
    // additional information which we are ignoring in this case. 
    return citeproc.appendCitationCluster( citation_object )[ 0 ][ 1 ];
}


function xinspect(o,i){
    if(typeof i=='undefined')i='';
    if(i.length>50)return '[MAX ITERATIONS]';
    var r=[];
    for(var p in o){
        var t=typeof o[p];
        r.push(i+'"'+p+'" ('+t+') => '+(t=='object' ? 'object:'+xinspect(o[p],i+'  ') : o[p]+''));
    }
    return r.join(i+'\n');
}

function get_formatted_bib(){
    // make the bibliography, and return it as a string. The method returns
    // additional information which we are ignoring in this case. 
	var bib = citeproc.makeBibliography();
	var str = "";
	for (var i=0;i<bib.length;i++)
	{
		str += bib[i]+"<br>"
	}
	output = citeproc.makeBibliography();
//	return output[0].bibstart + output[1].join("") + output[0].bibend;
	return xinspect(output);
}