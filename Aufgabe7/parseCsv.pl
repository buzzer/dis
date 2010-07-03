#!/usr/bin/perl
# Parse a csv file
# output an sql to load data into db

use warnings;
use strict;
#use Encode;
#use Encode::Detect::Detector;
#use utf8;
#use Text::Unidecode;

my $fpath = "sales_s.csv";
#my $fpath = "encodingtest.sql";
my $fpathout = "insertSales.sql";

open (my $fh, '<', $fpath) || die "Cannot open file $fpath: $!";
open (my $fhout, '>', $fpathout) || die "Cannot open file $fpathout: $!";

print $fhout "-- AUTOMATICALLY GENERATED FILE\n-- Any changes will be lost\n";

# Omit 1st (head) line
$_ = <$fh>;

while ( <$fh> ) {
  chomp $_;
  #print $_;
  #my $encoding_name=Encode::Detect::Detector::detect($_);
  #print $encoding_name;
  #my $string = decode ($encoding_name, $_);
  #print $string;
  #$_= unidecode($_);
  #print $_;
  if ($_ =~ m/^[\w]+.*$/) { # Ignore empty lines
    my @line = split(/;/,$_);
    $line[4] =~ s/,/./g; # replace ',' by '.'
    my $outline="insert into aufg7_csvtabelle (datum,shop,artikel,verkauft,umsatz) values (DATE ('$line[0]'),'$line[1]','$line[2]',$line[3],$line[4]);"."\n";
    #$outline=encode("iso-8859-1",$outline);
    #$outline=encode("utf8",$outline);
    #$outline=encode("latin1",$outline);
    #print my @all_enc=Encode->encodings("Encode::JP");
    print $fhout $outline;
    #print $outline;
  }
}

close ($fhout);
close ($fh);


exit 0;
