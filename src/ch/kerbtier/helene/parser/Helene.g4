grammar Helene;

root            : entity+;

entity          : identifier ':'  type;


type            : string | user | date | integer | slug | list | map;

string          : STRING;

slug            : SLUG;

user            : USER;

date            : DATE;

integer         : INTEGER;

list            : '[' type ']';

map             : '{' entity+ '}';


identifier        : IDENTIFIER | STRING | USER | DATE | INTEGER | SLUG;

STRING            : 'string';

SLUG              : 'slug';

USER              : 'user';

DATE              : 'date';

INTEGER           : 'integer';

IDENTIFIER        : [A-Za-z] ([A-Za-z0-9_])+;

BLOCK_COMMENT     : '/*' .*? '*/' -> skip;
EOL_COMMENT       : '//' ~[\r\n]* -> skip;

WS                : [ \n\t\r]+ -> skip;

